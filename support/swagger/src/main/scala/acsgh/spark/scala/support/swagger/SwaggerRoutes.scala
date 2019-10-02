package acsgh.spark.scala.support.swagger

import acsgh.spark.scala.support.swagger.dsl.OpenApiBuilder
import acsgh.spark.scala.{RequestContext, Spark}
import io.swagger.v3.core.util.{Json, Yaml}
import io.swagger.v3.oas.models.{OpenAPI, Operation, PathItem, Paths}
import spark.Response

trait SwaggerRoutes extends Spark with OpenApiBuilder {

  def swaggerRoutes(docPath: String = "/api-docs")(implicit openAPi: OpenAPI): Unit = {
    webjars()
    resourceFolder("/*", "swagger-ui")

    get(s"$docPath.json") { implicit context =>
      responseHeader("Content-Type", "application/json") {
        responseBody(Json.pretty().writeValueAsString(openAPi))
      }
    }

    get(s"$docPath.yaml") { implicit context =>
      responseHeader("Content-Type", "application/yaml") {
        responseBody(Yaml.pretty().writeValueAsString(openAPi))
      }
    }
  }

  def options(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "OPTIONS", operation)
    options(uri)(action)
  }

  def get(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "GET", operation)
    get(uri)(action)
  }

  def head(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "HEAD", operation)
    head(uri)(action)
  }

  def post(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "POST", operation)
    post(uri)(action)
  }

  def put(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "PUT", operation)
    put(uri)(action)
  }

  def patch(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "PATCH", operation)
    patch(uri)(action)
  }

  def delete(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "DELETE", operation)
    delete(uri)(action)
  }

  def trace(uri: String, operation: Operation)(action: RequestContext => Response)(implicit openAPi: OpenAPI): Unit = {
    registerAction(uri, "TRACE", operation)
    trace(uri)(action)
  }

  protected def registerAction(uri: String, method: String, operation: Operation)(implicit openAPi: OpenAPI): Unit = {
    if (openAPi.getPaths == null) {
      openAPi.setPaths(new Paths)
    }

    val finalUri = swaggeryfyURI(uri)

    openAPi.getPaths.putIfAbsent(finalUri, new PathItem())

    val item = openAPi.getPaths.get(finalUri)

    method match {
      case "OPTIONS" =>
        item.options(operation)
      case "GET" =>
        item.get(operation)
      case "HEAD" =>
        item.head(operation)
      case "POST" =>
        item.post(operation)
      case "PUT" =>
        item.put(operation)
      case "PATCH" =>
        item.patch(operation)
      case "DELETE" =>
        item.delete(operation)
      case "TRACE" =>
        item.trace(operation)
    }
  }

  def swaggeryfyURI(uri: String): String = uri.replaceAll(":(\\w+)", "{$1}")
}
