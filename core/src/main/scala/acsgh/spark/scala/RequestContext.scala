package acsgh.spark.scala

import java.net.URI

import acsgh.spark.scala.implicits._
import spark.{Request, Response}

import scala.collection.JavaConverters._

case class RequestContext
(
  route: String,
  request: Request,
  response: Response
) extends URLSupport {
  val requestHeaders: Map[String, List[String]] = request.raw().getHeaderNames.toList.map(name => (name, request.raw().getHeaders(name).toList)).toMap
  val queryParams: Map[String, List[String]] = request.queryParams().asScala.map(name => (name, request.queryParamsValues(name).toList)).toMap
  val pathParams: Map[String, String] = extractPathParams(route, URI.create(request.uri))

  private[scala] def validContentType(contentTypes: Set[String], contentType: String) = contentTypes.isEmpty || contentTypes.exists(t => t.equalsIgnoreCase(contentType))

}
