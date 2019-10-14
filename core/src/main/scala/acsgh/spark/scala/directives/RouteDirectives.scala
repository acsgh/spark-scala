package acsgh.spark.scala.directives

import java.net.URI

import acsgh.spark.scala.{RedirectStatus, RequestContext, ResponseStatus}
import spark.{Response, Service}

trait RouteDirectives extends ResponseDirectives {

  protected val service: Service

  def redirect(url: String, redirectStatus: RedirectStatus = RedirectStatus.FOUND)(implicit context: RequestContext): Response = {
    context.response.redirect(url, redirectStatus.status.code)
    context.response
  }

  def halt(input: ResponseStatus)(implicit context: RequestContext): Response = {
    service.halt(input.code)
    context.response
  }

  def halt(input: ResponseStatus, message: String)(implicit context: RequestContext): Response = {
    service.halt(input.code, message)
    context.response
  }

//  def serve(url: String)(implicit context: RequestContext): Response = {
//
//    context.router.process(context.request.copy(uri = URI.create(url)))
//  }
}
