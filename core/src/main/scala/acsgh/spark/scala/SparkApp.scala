package acsgh.spark.scala

import com.acsgh.common.scala.App

trait SparkApp extends App with SparkDelegate {

  protected val spark: Spark = Spark()

  onStart {
    spark.start()
  }

  onStop {
    spark.stop()
  }
}
