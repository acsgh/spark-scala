package acsgh.spark.scala

import acsgh.spark.scala.implicits._
import spark.{Request, Response}

import scala.jdk.CollectionConverters._

case class RequestContext
(
  route: Option[String],
  request: Request,
  response: Response
) {
  val requestHeaders: Map[String, List[String]] = request.raw().getHeaderNames.toList.map(name => (name, request.raw().getHeaders(name).toList)).toMap
  val queryParams: Map[String, List[String]] = request.queryParams().asScala.map(name => (name, request.queryParamsValues(name).toList)).toMap
  val cookieParams: Map[String, String] = Option(request.headers("Cookie")).toList.flatMap(_.split(";")).map(extractCookie).toMap
  val pathParams: Map[String, String] = Map() ++ request.params.asScala.map(e => (e._1.replace(":", ""), e._2))

  private[scala] def validContentType(contentTypes: Set[String], contentType: String) = contentTypes.isEmpty || contentTypes.exists(t => t.equalsIgnoreCase(contentType))

  private def extractCookie(input: String): (String, String) = {
    val parts = input.trim.split("=")
    (parts(0), parts(1))
  }
}
