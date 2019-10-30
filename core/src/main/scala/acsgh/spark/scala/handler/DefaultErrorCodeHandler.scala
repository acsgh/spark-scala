package acsgh.spark.scala.handler

import acsgh.spark.scala.{RequestContext, ResponseStatus, Spark}
import spark.Response

class DefaultErrorCodeHandler(override val spark: Spark) extends ErrorCodeHandler {

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
