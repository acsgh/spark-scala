package acsgh.spark.scala.converter.template.thymeleaf

import java.io.{ByteArrayOutputStream, PrintWriter}

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.directives.Directives
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import spark.Response

import scala.language.implicitConversions

trait ThymeleafDirectives {
  directives: Directives =>

  implicit protected def toContext(map: Map[String, Any]): Context = {
    val context = new Context
    map.foreach(e => context.setVariable(e._1, e._2))
    context
  }

  def thymeleafTemplate(templateName: String, params: Map[String, String])(implicit context: RequestContext, thymeleafEngine: TemplateEngine): Response = {
    val out = new ByteArrayOutputStream
    try {
      val printStream = new PrintWriter(out)
      thymeleafEngine.process(templateName, params, printStream)
      responseBody(out.toByteArray)
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    } finally {
      if (out != null) out.close()
    }
  }
}
