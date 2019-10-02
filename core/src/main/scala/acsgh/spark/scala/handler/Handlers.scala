package acsgh.spark.scala.handler

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.{RequestContext, ResponseStatus}
import spark.Response

trait ErrorCodeHandler extends Directives {
  def handle(responseStatus: ResponseStatus)(implicit requestContext: RequestContext): Response
}

trait ExceptionHandler extends Directives {
  def handle(exception: Exception)(implicit requestContext: RequestContext): Response
}
