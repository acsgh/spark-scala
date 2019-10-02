package acsgh.spark.scala.handler

import acsgh.spark.scala.{RequestContext, ResponseStatus}
import spark.{Response, Service}

class DefaultErrorCodeHandler(protected val service: Service, protected val productionMode: Boolean) extends ErrorCodeHandler {
  override def handle(status: ResponseStatus)(implicit requestContext: RequestContext): Response = {
    responseStatus(status) {
      responseBody(getStatusBody(status))
    }
  }

  private def getStatusBody(status: ResponseStatus): String = {
    s"""<html>
       |<head>
       |   <title>${status.code} - ${status.message}</title>
       |</head>
       |<body>
       |   <h1>${status.code} - ${status.message}</h1>
       |</body>
       |</html>""".stripMargin
  }
}
