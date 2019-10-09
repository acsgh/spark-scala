package acsgh.spark.scala.sparkParts

import acsgh.spark.scala.handler._
import acsgh.spark.scala.{RequestContext, ResponseStatus}
import spark.{ExceptionHandler => _, _}

trait SparkHandler extends SparkFilter {

  private var exceptionHandler: ExceptionHandler = new DefaultExceptionHandler()
  private var handlers: List[EventListener] = List()

  def initHandlers(): Unit = {
    beforeAll { implicit ctx =>
      handlers.foreach(_.onStart())
      ctx.response
    }

    afterAfterAll { implicit ctx =>
      handlers.foreach(_.onStop())
      ctx.response
    }

    service.exception(classOf[java.lang.Exception], (exception: Exception, request: Request, response: Response) => {
      implicit val ctx: RequestContext = toContext(request, response)
      handlers.foreach(_.onException(exception))
      exceptionHandler.handle(exception)
    })

  }

  def exceptionHandler(handler: ExceptionHandler): Unit = {
    exceptionHandler = handler
  }

  def addHandler(listener: EventListener): Unit = {
    handlers = handlers ++ List(listener)
  }

  def removeHandler(listener: EventListener): Unit = {
    handlers = handlers.filterNot(_ == listener)
  }

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
}
