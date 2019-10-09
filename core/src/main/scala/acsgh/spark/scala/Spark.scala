package acsgh.spark.scala

import acsgh.spark.scala.sparkParts.{SparkBase, SparkFilter, SparkHandler, SparkServlet}
import spark.Service
import spark.route.Routes

object Spark {

  private val ROUTES = "routes"

  private[scala] val service: Service = Service.ignite()

  private[scala] var productionEnvironment: Boolean = false

  private[scala] lazy val sparkRoutes: Routes = {
    val sparkRoutesField = classOf[Service].getDeclaredField(ROUTES)
    sparkRoutesField.setAccessible(true)
    sparkRoutesField.get(service).asInstanceOf[Routes]
  }
}

trait Spark extends SparkBase with SparkServlet with SparkFilter with SparkHandler
