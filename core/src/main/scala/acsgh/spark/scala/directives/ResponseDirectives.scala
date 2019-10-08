package acsgh.spark.scala.directives

import acsgh.spark.scala.convertions.{BodyWriter, DefaultFormats, DefaultParamHandling, ParamWriter}
import acsgh.spark.scala.{RequestContext, ResponseStatus}
import spark.{Response, Service}

trait ResponseDirectives extends DefaultParamHandling with DefaultFormats {

  protected val service: Service

  def responseHeader[T](name: String, value: T)(action: => Response)(implicit context: RequestContext, converter: ParamWriter[T]): Response = {
    context.response.header(name, converter.write(value))
    action
  }

  def responseStatus(input: ResponseStatus)(action: => Response)(implicit context: RequestContext): Response = {
    context.response.status(input.code)
    action
  }

  def halt(input: ResponseStatus)(implicit context: RequestContext): Response = {
    service.halt(input.code)
    context.response
  }

  def halt(input: ResponseStatus, message: String)(implicit context: RequestContext): Response = {
    service.halt(input.code, message)
    context.response
  }

  def responseBody(input: Array[Byte])(implicit context: RequestContext): Response = {
    context.response.body("")
    context.response.raw().getOutputStream.write(input)
    context.response
  }

  def responseBody[T](input: T)(implicit context: RequestContext, writer: BodyWriter[T]): Response = {
    if (context.response.raw().getHeader("Content-Type") == null) {
      responseHeader("Content-Type", writer.contentType) {
        responseBody(writer.write(input))
      }
    } else {
      responseBody(writer.write(input))
    }
  }
}
