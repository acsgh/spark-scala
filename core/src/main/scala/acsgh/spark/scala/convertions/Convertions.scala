package acsgh.spark.scala.convertions

trait ParamReader[T] {
  def read(input: String): T
}

trait ParamWriter[T] {
  def write(input: T): String
}

trait BodyReader[T] {
  val contentTypes: Set[String] = Set()

  def read(body: Array[Byte]): T
}

trait BodyWriter[T] {
  val contentType: String = "text/html"

  def write(body: T): Array[Byte]
}