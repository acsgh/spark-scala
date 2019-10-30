package acsgh.spark.scala

import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

import acsgh.spark.scala.files.{StaticClasspathFolderFilter, StaticFilesystemFolderFilter}
import acsgh.spark.scala.handler.{DefaultExceptionHandler, ErrorCodeHandler, EventListener, ExceptionHandler}
import spark._
import spark.route.{HttpMethod, Routes}

case class Spark() {
  private val ROUTES = "routes"

  private val started: AtomicBoolean = new AtomicBoolean(false)
  private var _productionMode: Boolean = false
  private var exceptionHandler: ExceptionHandler = new DefaultExceptionHandler(this)
  private var handlers: List[EventListener] = List()
  private var routingActions: List[() => Unit] = List()
  private var service: Service = ignite()

  private var sparkRoutes: Routes = _

  def start(): Unit = {
    if (started.compareAndSet(false, true)) {
      routingActions.foreach(_.apply())

      sparkRoutes = {
        val sparkRoutesField = classOf[Service].getDeclaredField(ROUTES)
        sparkRoutesField.setAccessible(true)
        sparkRoutesField.get(service).asInstanceOf[Routes]
      }

      service.init()
    }
  }

  def stop(): Unit = {
    if (started.compareAndSet(true, false)) {
      service.stop()
      sparkRoutes = null
      service = ignite()
    }
  }

  def productionMode(value: Boolean): Unit = {
    checkNotStarted()
    _productionMode = value
  }

  def productionMode: Boolean = _productionMode

  def activeThreadCount(): Int = {
    checkNotStarted()
    service.activeThreadCount()
  }

  def threadPool(maxThreads: Int, minThreads: Int, idleTimeoutMillis: Int): Service = {
    checkNotStarted()
    service.threadPool(maxThreads, minThreads, idleTimeoutMillis)
  }

  def threadPool(maxThreads: Int): Service = {
    checkNotStarted()
    service.threadPool(maxThreads)
  }

  def port: Int = service.port()

  def port(port: Int): Service = {
    checkNotStarted()
    service.port(port)
  }

  def ipAddress(ipAddress: String): Service = {
    checkNotStarted()
    service.ipAddress(ipAddress)
  }

  def exceptionHandler(handler: ExceptionHandler): Unit = {
    checkNotStarted()
    exceptionHandler = handler
  }

  def addHandler(listener: EventListener): Unit = {
    checkNotStarted()
    handlers = handlers ++ List(listener)
  }

  def removeHandler(listener: EventListener): Unit = {
    checkNotStarted()
    handlers = handlers.filterNot(_ == listener)
  }

  def errorPageHandler(responseStatus: ResponseStatus, handler: ErrorCodeHandler): Unit = {
    checkNotStarted()

    val method = classOf[CustomErrorPages].getDeclaredMethod("add", Integer.TYPE, classOf[Route])
    method.setAccessible(true)

    val code: java.lang.Integer = responseStatus.code

    val route: Route = (request: Request, response: Response) => {
      implicit val context: RequestContext = toContext(request, response)
      handler.handle(responseStatus)
      ""
    }
    method.invoke(null, code, route)
  }

  def halt(input: ResponseStatus): Unit = {
    service.halt(input.code)
  }

  def halt(input: ResponseStatus, message: String): Unit = {
    service.halt(input.code, message)
  }

  def before(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.before(url, new Filter {
        override def handle(request: Request, response: Response): Unit = {
          action(toContext(url, request, response))
        }
      })
    )
  }

  def after(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.after(url, new Filter {
        override def handle(request: Request, response: Response): Unit = {
          action(toContext(url, request, response))
        }
      })
    )
  }

  def afterAfter(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.afterAfter(url, new Filter {
        override def handle(request: Request, response: Response): Unit = {
          action(toContext(url, request, response))
        }
      })
    )
  }

  def resourceFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticClasspathFolderFilter(resourceFolderPath)(this).handle)

  def filesystemFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticFilesystemFolderFilter(new File(resourceFolderPath))(this).handle)

  def webjars(): Unit = resourceFolder("/webjars/*", "META-INF/resources/webjars")

  def get(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.get(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def post(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.post(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def put(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.put(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def patch(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.patch(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def delete(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.delete(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def head(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.head(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def trace(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.trace(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def connect(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.connect(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  def options(url: String)(action: RequestContext => Response): Unit = {
    addRoutingAction(() =>
      service.options(url, (request, response) => {
        action(toContext(request, response))
        ""
      })
    )
  }

  private def toContext(request: Request, response: Response): RequestContext = RequestContext(
    route(request),
    request,
    response
  )

  private def toContext(url: String, request: Request, response: Response): RequestContext = RequestContext(
    route(request).orElse(Some(url)),
    request,
    response
  )

  private def route(request: Request): Option[String] =
    Option(
      sparkRoutes.find(
        HttpMethod.get(
          request.requestMethod.toLowerCase
        ),
        request.uri,
        null
      )
    )
      .map(_.getMatchUri)
      .map(s => if (s.startsWith("/")) s else s"/$s")

  private def ignite(): Service = {
    routingActions = List()

    addRoutingAction(() => {
      service.exception(classOf[java.lang.Exception], (exception: Exception, request: Request, response: Response) => {
        implicit val ctx: RequestContext = toContext(request, response)
        handlers.foreach(_.onException(exception))
        exceptionHandler.handle(exception)
      })
    })

    before("/*") { implicit ctx =>
      handlers.foreach(_.onStart())
      ctx.response
    }

    afterAfter("/*") { implicit ctx =>
      handlers.foreach(_.onStop())
      ctx.response
    }

    Service.ignite()
  }

  private def addRoutingAction(action: () => Unit): Unit = {
    checkNotStarted()
    routingActions = routingActions ++ List(action)
  }

  private def checkNotStarted(): Unit = {
    if (started.get()) {
      throw new IllegalArgumentException("This action can only be performed before start the service")
    }
  }
}
