package acsgh.spark.scala.converter.json.jackson

import acsgh.spark.scala.SparkApp
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

trait JacksonSparkApp extends JacksonDirectives {
  app: SparkApp =>

  protected implicit val objectMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper
  }
}
