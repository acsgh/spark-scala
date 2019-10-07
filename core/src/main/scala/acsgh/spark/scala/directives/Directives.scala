package acsgh.spark.scala.directives

import acsgh.spark.scala.Spark
import com.acsgh.common.scala.log.LogSupport

trait Directives extends RequestDirectives with ResponseDirectives with RouteDirectives with LogSupport {
  protected def productionMode: Boolean = Spark.productionEnvironment
}
