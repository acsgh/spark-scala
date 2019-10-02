package acsgh.spark.scala

import com.acsgh.common.scala.App

trait SparkApp extends App with Spark {

  protected val productionMode: Boolean = false

  onConfigure {
    defaultExceptionHandler()
  }

  onStart {
    service.init()
  }

  onStop {
    service.stop()
  }
}
