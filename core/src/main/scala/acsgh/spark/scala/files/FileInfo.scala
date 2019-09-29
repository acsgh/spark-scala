package acsgh.spark.scala.files

import java.text.ParseException
import java.util.Date

import com.acsgh.common.scala.log.LogSupport

case class FileInfo
(
  contentType: String,
  etag: String,
  lastModified: Date,
  private var contentSupplier: () => Array[Byte]
) extends LogSupport {

  private[files] def isModified(ifNoneMatchHeader: Option[String], ifModifiedSinceHeader: Option[String]): Boolean = {
    ifModifiedSinceHeader.map { expectedDate =>
      try {
        val lastModifiedClient = FileFilter.DATE_FORMATTER.parse(expectedDate)
        lastModified.after(lastModifiedClient)
        false
      } catch {
        case e: ParseException =>
          log.debug("Unable to parse date", e)
          true
      }
    }.orElse({
      ifNoneMatchHeader.map { header =>
        header.equalsIgnoreCase(etag)
      }
    }).getOrElse(true)

  }

  private[files] def content = contentSupplier()
}
