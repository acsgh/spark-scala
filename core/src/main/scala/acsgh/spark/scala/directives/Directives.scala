package acsgh.spark.scala.directives

import com.acsgh.common.scala.log.LogSupport

trait Directives extends RequestDirectives with ResponseDirectives with RouteDirectives with LogSupport {
}
