package acsgh.spark.scala.convertions

trait DefaultFormats {

  implicit object HtmlBodyWriter extends BodyWriter[String] {
    override val contentType: String = "text/html; charset=UTF-8"

    override def write(body: String): Array[Byte] = body.getBytes("UTF-8")
  }

}
