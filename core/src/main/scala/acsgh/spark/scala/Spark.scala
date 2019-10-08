package acsgh.spark.scala

import java.io.File

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.files.{StaticClasspathFolderFilter, StaticFilesystemFolderFilter}
import acsgh.spark.scala.handler.{DefaultExceptionHandler, ErrorCodeHandler, ExceptionHandler}
import spark._
import spark.route.{HttpMethod, Routes}

object Spark {

  private val ROUTES = "routes"

  private[scala] val service: Service = Service.ignite()

  private[scala] var productionEnvironment: Boolean = false

  private[scala] lazy val sparkRoutes: Routes = {
    val sparkRoutesField = classOf[Service].getDeclaredField(ROUTES)
    sparkRoutesField.setAccessible(true)
    sparkRoutesField.get(service).asInstanceOf[Routes]
  }
}

trait Spark extends Directives {
  protected implicit val service: Service = Spark.service

  def production(value: Boolean): Unit = {
    Spark.productionEnvironment = value
  }

  def activeThreadCount(): Int = service.activeThreadCount()

  def threadPool(maxThreads: Int, minThreads: Int, idleTimeoutMillis: Int): Service = service.threadPool(maxThreads, minThreads, idleTimeoutMillis)

  def threadPool(maxThreads: Int): Service = service.threadPool(maxThreads)

  def port(): Int = service.port()

  def port(port: Int): Service = service.port(port)

  def ipAddress(ipAddress: String): Service = service.ipAddress(ipAddress)

  def get(url: String)(action: RequestContext => Response): Unit = {
    service.get(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def post(url: String)(action: RequestContext => Response): Unit = {
    service.post(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def put(url: String)(action: RequestContext => Response): Unit = {
    service.put(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def patch(url: String)(action: RequestContext => Response): Unit = {
    service.patch(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def delete(url: String)(action: RequestContext => Response): Unit = {
    service.delete(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def head(url: String)(action: RequestContext => Response): Unit = {
    service.head(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def trace(url: String)(action: RequestContext => Response): Unit = {
    service.trace(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def connect(url: String)(action: RequestContext => Response): Unit = {
    service.connect(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def options(url: String)(action: RequestContext => Response): Unit = {
    service.options(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def before(action: RequestContext => Response): Unit = {
    service.before(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def after(action: RequestContext => Response): Unit = {
    service.after(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def afterAfter(action: RequestContext => Response): Unit = {
    service.afterAfter(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def before(url: String)(action: RequestContext => Response): Unit = {
    service.before(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def after(url: String)(action: RequestContext => Response): Unit = {
    service.after(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def afterAfter(url: String)(action: RequestContext => Response): Unit = {
    service.afterAfter(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def resourceFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticClasspathFolderFilter(resourceFolderPath).handle)

  def filesystemFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticFilesystemFolderFilter(new File(resourceFolderPath)).handle)

  def webjars(): Unit = resourceFolder("/webjars/*", "META-INF/resources/webjars")

  def exceptionHandler(handler: ExceptionHandler): Unit = {
    service.exception(classOf[java.lang.Exception], (exception: Exception, request: Request, response: Response) => {
      implicit val context: RequestContext = toContext(request, response)
      handler.handle(exception)
    })
  }

  def defaultExceptionHandler(): Unit = exceptionHandler(new DefaultExceptionHandler())

  def errorPageHandler(responseStatus: ResponseStatus, handler: ErrorCodeHandler): Unit = {
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


  private def toContext(request: spark.Request, response: spark.Response): RequestContext = RequestContext(
    route(request),
    request,
    response
  )

  private def toContext(url:String, request: spark.Request, response: spark.Response): RequestContext = RequestContext(
    route(request).orElse(Some(url)),
    request,
    response
  )

  private def route(request: Request): Option[String] =
    Option(
      Spark.sparkRoutes.find(
        HttpMethod.get(
          request.requestMethod.toLowerCase),
        request.uri,
        null
      )
    )
      .map(_.getMatchUri)
      .map(s => if (s.startsWith("/")) s else s"/$s")
}
