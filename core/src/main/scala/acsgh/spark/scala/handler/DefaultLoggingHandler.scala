package acsgh.spark.scala.handler

import java.util.concurrent.TimeUnit

import acsgh.spark.scala.{RequestContext, Spark}
import com.acsgh.common.scala.time.TimerSplitter
import spark.{Response, Service}

class DefaultLoggingHandler(delegate: ExceptionHandler = new DefaultExceptionHandler()) extends LoggingHandler {

  protected val START_TIME = "START_TIME"

  protected val service: Service = Spark.service

  def onStart()(implicit ctx: RequestContext): Response = {
    log.debug(s"Request: ${ctx.request.requestMethod} ${ctx.request.uri}")

    ctx.request.attribute(START_TIME, System.currentTimeMillis)
    ctx.response
  }

  def onStop()(implicit ctx: RequestContext): Response = {
    val startTime: Long = ctx.request.attribute(START_TIME)
    val duration = System.currentTimeMillis - startTime

    log.debug(s"Request: ${ctx.request.requestMethod} ${ctx.request.uri} in ${TimerSplitter.getIntervalInfo(duration, TimeUnit.MILLISECONDS)}")

    ctx.response
  }

  def handle(exception: Exception)(implicit ctx: RequestContext): Response = {
    log.error(s"Error during request: ${ctx.request.requestMethod}: ${ctx.request.uri} - Body: '${ctx.request.body}'", exception)
    delegate.handle(exception)
  }
}
