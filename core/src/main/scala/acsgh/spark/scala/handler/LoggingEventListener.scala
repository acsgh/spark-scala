package acsgh.spark.scala.handler

import java.util.concurrent.TimeUnit

import acsgh.spark.scala.implicits._
import acsgh.spark.scala.{RequestContext, Spark}
import com.acsgh.common.scala.time.TimerSplitter
import spark.Service

class LoggingEventListener() extends EventListener {

  protected val service: Service = Spark.service

  def onStart()(implicit ctx: RequestContext): Unit = {
    log.debug(s"Request:  ${ctx.request.requestMethod} ${ctx.request.uri}")
    ctx.request.saveStartTime()
  }

  def onStop()(implicit ctx: RequestContext): Unit = {
    val duration = ctx.request.timeSpent

    log.debug(s"Response: ${ctx.request.requestMethod} ${ctx.request.uri} with ${ctx.response.status()} in ${TimerSplitter.getIntervalInfo(duration, TimeUnit.MILLISECONDS)}")
  }

  def onException(exception: Exception)(implicit ctx: RequestContext): Unit = {
    log.error(s"Error during request: ${ctx.request.requestMethod}: ${ctx.request.uri} - Body: '${ctx.request.body}'", exception)
  }
}
