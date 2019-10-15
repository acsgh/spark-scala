package acsgh.spark.scala.converter.template.twirl

import acsgh.spark.scala.convertions.BodyWriter
import acsgh.spark.scala.directives.Directives
import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import play.twirl.api.HtmlFormat


trait TwirlSupport extends Directives {

  private val htmlCompressorFilter: HtmlCompressor = {
    val c = new HtmlCompressor()
    c.setPreserveLineBreaks(false)
    c.setRemoveComments(true)
    c.setRemoveIntertagSpaces(true)
    c.setRemoveHttpProtocol(true)
    c.setRemoveHttpsProtocol(true)
    c
  }

  implicit object TwirlBodyWriter extends BodyWriter[HtmlFormat.Appendable] {
    override val contentType: String = "text/html; charset=UTF-8"

    override def write(input: HtmlFormat.Appendable): Array[Byte] = {
      val body = if (productionMode) htmlCompressorFilter.compress(input.body) else input.body
      body.getBytes("UTF-8")
    }
  }
}
