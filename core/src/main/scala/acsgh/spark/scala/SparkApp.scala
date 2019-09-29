package acsgh.spark.scala

import com.acsgh.common.scala.App
import spark.Spark._

trait SparkApp extends App with Spark {

  protected val productionMode: Boolean = false

  onConfigure{

  }

  onStart{
    service.init()
  }

  onStop{
    service.stop()
  }
}
