package acsgh.spark.scala.directives

import acsgh.spark.scala.{RedirectStatus, RequestContext, ResponseStatus, Spark}
import spark.Response

trait RouteDirectives extends ResponseDirectives {

  protected def spark: Spark

  def redirect(url: String, redirectStatus: RedirectStatus = RedirectStatus.FOUND)(implicit context: RequestContext): Response = {
    context.response.redirect(url, redirectStatus.status.code)
    context.response
  }

  def halt(input: ResponseStatus)(implicit context: RequestContext): Response = {
    spark.halt(input)
    context.response
  }

  def halt(input: ResponseStatus, message: String = null)(implicit context: RequestContext): Response = {
    spark.halt(input, message)
    context.response
  }
}
