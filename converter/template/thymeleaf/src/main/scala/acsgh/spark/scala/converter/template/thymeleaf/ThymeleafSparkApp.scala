package acsgh.spark.scala.converter.template.thymeleaf

import acsgh.spark.scala.{Spark, SparkApp}
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.{ClassLoaderTemplateResolver, FileTemplateResolver}

trait ThymeleafSparkApp extends ThymeleafDirectives {
  app: SparkApp =>

  protected val source: ThymeleafSource = ThymeleafSource.Classpath
  protected val prefix: String
  protected val suffix: String = ".html"
  protected val templateMode: TemplateMode = TemplateMode.HTML
  protected val encoding: String = "UTF-8"

  protected implicit var thymeleafEngine: TemplateEngine = new TemplateEngine

  onConfigure {
    configureTemplateEngine()
  }

  private def configureTemplateEngine(): Unit = {
    try {
      val templateResolver = source match {
        case ThymeleafSource.File =>
          new FileTemplateResolver
        case ThymeleafSource.Classpath =>
          new ClassLoaderTemplateResolver
        case _ =>
          throw new NullPointerException("Source " + source + " is not valid")
      }
      templateResolver.setTemplateMode(templateMode)
      templateResolver.setPrefix(prefix)
      templateResolver.setSuffix(suffix)
      templateResolver.setCharacterEncoding(encoding)
      thymeleafEngine.setTemplateResolver(templateResolver)
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
  }
}
