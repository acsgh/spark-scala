package acsgh.spark.scala.convertions

trait DefaultFormats {

  implicit object HtmlBodyWriter extends BodyWriter[String] {
    override val contentType: String = "text/html"

    override def write(body: String): Array[Byte] = body.getBytes("UTF-8")
  }

}
