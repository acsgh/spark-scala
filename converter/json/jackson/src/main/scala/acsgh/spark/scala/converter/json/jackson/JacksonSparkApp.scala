package acsgh.spark.scala.converter.json.jackson

import acsgh.spark.scala.SparkApp
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
    import com.fasterxml.jackson.databind.SerializationFeature

trait JacksonSparkApp extends JacksonDirectives {
  app: SparkApp =>

  protected implicit val objectMapper: ObjectMapper = {
    val mapper = new ObjectMapper with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(SerializationFeature.INDENT_OUTPUT, !productionMode)
    mapper
  }
}
