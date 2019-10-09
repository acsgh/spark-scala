package acsgh.spark.scala.handler

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.{RequestContext, ResponseStatus}
import com.acsgh.common.scala.log.LogSupport
import spark.Response

trait ErrorCodeHandler extends Directives {
  def handle(responseStatus: ResponseStatus)(implicit requestContext: RequestContext): Response
}

trait ExceptionHandler extends Directives {
  def handle(exception: Exception)(implicit requestContext: RequestContext): Response
}

trait EventListener extends LogSupport {
  def onStart()(implicit requestContext: RequestContext): Unit

  def onStop()(implicit requestContext: RequestContext): Unit

  def onException(exception: Exception)(implicit requestContext: RequestContext): Unit
}
