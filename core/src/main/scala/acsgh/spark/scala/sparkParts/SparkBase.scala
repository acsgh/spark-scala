package acsgh.spark.scala.sparkParts

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.{RequestContext, Spark}
import spark.route.HttpMethod
import spark.{Request, Response, Service}


trait SparkBase extends Directives {

  protected implicit val service: Service = Spark.service

  def production(value: Boolean): Unit = {
    Spark.productionEnvironment = value
  }

  def activeThreadCount(): Int = service.activeThreadCount()

  def threadPool(maxThreads: Int, minThreads: Int, idleTimeoutMillis: Int): Service = service.threadPool(maxThreads, minThreads, idleTimeoutMillis)

  def threadPool(maxThreads: Int): Service = service.threadPool(maxThreads)

  def port(): Int = service.port()

  def port(port: Int): Service = service.port(port)

  def ipAddress(ipAddress: String): Service = service.ipAddress(ipAddress)

  protected def toContext(request: Request, response: Response): RequestContext = RequestContext(
    route(request),
    request,
    response
  )

  protected def toContext(url: String, request: Request, response: Response): RequestContext = RequestContext(
    route(request).orElse(Some(url)),
    request,
    response
  )

  protected def route(request: Request): Option[String] =
    Option(
      Spark.sparkRoutes.find(
        HttpMethod.get(
          request.requestMethod.toLowerCase
        ),
        request.uri,
        null
      )
    )
      .map(_.getMatchUri)
      .map(s => if (s.startsWith("/")) s else s"/$s")
}
