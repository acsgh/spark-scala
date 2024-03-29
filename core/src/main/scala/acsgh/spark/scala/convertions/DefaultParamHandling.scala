package acsgh.spark.scala.convertions

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.directives._

import scala.language.implicitConversions

trait DefaultParamHandling {

  implicit object StringWriter extends ParamWriter[String] {
    override def write(input: String): String = input
  }

  implicit object StringReader extends ParamReader[String] {
    override def read(input: String): String = input
  }

  implicit object LongWriter extends ParamWriter[Long] {
    override def write(input: Long): String = input.toString
  }

  implicit object LongReader extends ParamReader[Long] {
    override def read(input: String): Long = input.toLong
  }

  implicit def string2Param(name: String)(implicit reader: ParamReader[String]): Param[String, String] = SingleParam[String](name)

  implicit class StringParamsEnhanced(name: String) {
    def as[T](implicit reader: ParamReader[T]): SingleParam[T] = SingleParam[T](name)

    def opt: OptionParam[String] = OptionParam[String](name)

    def default(defaultValue: String): DefaultParam[String] = DefaultParam[String](name, defaultValue)

    def list: ListParam[String] = ListParam[String](name)
  }

  implicit class SingleParamEnhanced[P](param: SingleParam[P])(implicit reader: ParamReader[P]) {
    def opt: OptionParam[P] = OptionParam[P](param.name)

    def default(defaultValue: P): DefaultParam[P] = DefaultParam[P](param.name, defaultValue)

    def list: ListParam[P] = ListParam[P](param.name)
  }

  implicit class ParamsEnhanced[P, R](param: Param[P, R]) {
    def queryValue(implicit context: RequestContext): R = {
      val value = context.queryParams.find(_._1.equalsIgnoreCase(param.name)).map(_._2).getOrElse(List())
      param("Query", value)
    }

    def pathValue(implicit context: RequestContext): R = {
      val value = context.pathParams.find(_._1.equalsIgnoreCase(param.name)).map(_._2).toList
      param("Path", value)
    }

    def cookieValue(implicit context: RequestContext): R = {
      val value = context.cookieParams.find(_._1.equalsIgnoreCase(param.name)).map(_._2).toList
      param("Cookie", value)
    }

    def headerValue(implicit context: RequestContext): R = {
      val value = context.requestHeaders.find(_._1.equalsIgnoreCase(param.name)).map(_._2).getOrElse(List())
      param("Header", value)
    }
  }
}
