package acsgh.spark.scala.directives

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.ResponseStatus.UNSUPPORTED_MEDIA_TYPE
import acsgh.spark.scala.convertions.{BodyReader, DefaultFormats, DefaultParamHandling}
import spark.Response

trait RequestDirectives extends DefaultParamHandling with DefaultFormats with RequestParamsDirectives with RequestHeaderDirectives with RequestQueryDirectives with RequestCookieDirectives with RouteDirectives {

  def requestBody(action: Array[Byte] => Response)(implicit context: RequestContext): Response = action(context.request.bodyAsBytes())

  def requestBody[T](action: T => Response)(implicit context: RequestContext, reader: BodyReader[T]): Response = {
    requestHeader("Content-Type") { contentType =>

      if (!context.validContentType(reader.contentTypes, contentType)) {
        error(UNSUPPORTED_MEDIA_TYPE)
      } else {
        action(reader.read(context.request.bodyAsBytes))
      }

    }
  }
}
