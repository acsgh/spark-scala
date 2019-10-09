package acsgh.spark.scala

import com.acsgh.common.scala.App

trait SparkApp extends App with Spark {

  onStart {
    service.init()
  }

  onStop {
    service.stop()
  }
}
