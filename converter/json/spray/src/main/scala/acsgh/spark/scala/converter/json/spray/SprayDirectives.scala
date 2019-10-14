package acsgh.spark.scala.converter.json.spray

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.convertions.{BodyReader, BodyWriter}
import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.exception.BadRequestException
import spark.Response
import spray.json._

trait SprayDirectives {
  directives: Directives =>

  protected def reader[T](clazz: Class[T], strictContentTypes: Boolean = false)(implicit jsonReader: JsonReader[T]): BodyReader[T] = new BodyReader[T] {
    override val contentTypes: Set[String] = Set("application/json")

    override val strictContentTypes: Boolean = strictContentTypes

    override def read(body: Array[Byte]): T = {
      try {
        new String(body, "UTF-8").parseJson.convertTo[T]
      } catch {
        case e: Exception => throw new BadRequestException(e)
      }
    }
  }

  protected def writer[T]()(implicit jsonWriter: JsonWriter[T]): BodyWriter[T] = new BodyWriter[T] {

    override val contentType: String = "application/json"

    override def write(body: T): Array[Byte] = {
      try {
        val json = if (productionMode) {
          body.toJson.compactPrint
        } else {
          body.toJson.prettyPrint
        }
        json.getBytes("UTF-8")
      } catch {
        case e: Exception => throw new RuntimeException(e)
      }
    }
  }

  def requestJson[T](clazz: Class[T], strictContentTypes: Boolean = false)(action: T => Response)(implicit context: RequestContext, jsonReader: JsonReader[T]): Response = requestBody(action)(context, reader(clazz, strictContentTypes))

  def responseJson[T](value: T)(implicit context: RequestContext, jsonWriter: JsonWriter[T]): Response = responseBody(value)(context, writer())

}
