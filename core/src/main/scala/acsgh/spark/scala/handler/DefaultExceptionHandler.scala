package acsgh.spark.scala.handler

import acsgh.spark.scala.exception.BadRequestException
import acsgh.spark.scala.{RequestContext, ResponseStatus, Spark}
import spark.{Response, Service}

object DefaultExceptionHandler {

  def stacktraceToHtml(throwable: Throwable): String = {
    var result = "<p>"
    result += stacktraceToHtmlInternal(throwable, causeThrowable = false)
    var cause = throwable.getCause

    while (cause != null) {
      result += stacktraceToHtmlInternal(cause, causeThrowable = true)
      cause = cause.getCause
    }

    result += "</p>"
    result
  }

  private def stacktraceToHtmlInternal(throwable: Throwable, causeThrowable: Boolean) = {
    var result = ""
    result += "<b>"
    if (causeThrowable) {
      result += "Caused by: "
    }
    result += throwable.getClass.getName + ":&nbsp;" + "</b>" + throwable.getMessage + "<br/>\n"
    for (stackTraceElement <- throwable.getStackTrace) {
      result += "&nbsp;&nbsp;&nbsp;&nbsp;" + stackTraceElement + "<br/>\n"
    }
    result
  }
}

class DefaultExceptionHandler(override val spark: Spark) extends ExceptionHandler {

  override def handle(exception: Exception)(implicit ctx: RequestContext): Response = {
    val status = if (exception.isInstanceOf[BadRequestException]) ResponseStatus.BAD_REQUEST else ResponseStatus.INTERNAL_SERVER_ERROR
    responseStatus(status) {
      responseBody(getStatusBody(status, exception))
    }
  }

  private def getStatusBody(status: ResponseStatus, throwable: Throwable): String = {
    s"""<html>
       |<head>
       |   <title>${status.code} - ${status.message}</title>
       |</head>
       |<body>
       |   <h2>${status.code} - ${status.message}</h2>
       |   ${if (spark.productionMode) "" else DefaultExceptionHandler.stacktraceToHtml(throwable)}
       |</body>
       |</html>""".stripMargin
  }
}
