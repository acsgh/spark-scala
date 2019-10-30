package acsgh.spark.scala.files

import java.net.URL
import java.time.Instant
import java.util.Date

import acsgh.spark.scala.Spark

case class StaticClasspathFolderFilter(private val baseFolder: String, classLoader: ClassLoader = Thread.currentThread().getContextClassLoader)(implicit protected val spark: Spark) extends FileFilter {

  override def getFileInfo(fileName: String): Option[FileInfo] = {
    val file = baseFolder + addTradingSlash(fileName)
    val url = classLoader.getResource(file)

    if ((url != null) && (!file.endsWith("/")) && fileExists(file)) {
      val contentSupplier = () => loadFileContent(file)
      Some(FileInfo(contentType(fileName), calculateEtag(contentSupplier()), lastModified(url), contentSupplier))
    } else {
      None
    }
  }

  private def loadFileContent(file: String) = {
    val input = classLoader.getResourceAsStream(file)
    try {
      bytes(input)
    } catch {
      case e: Exception =>
        log.info("Unable to read file: " + file, e)
        new Array[Byte](0)
    } finally {
      if (input != null) {
        input.close()
      }
    }
  }

  private def fileExists(file: String) = {
    val input = classLoader.getResourceAsStream(file)
    try {
      input != null
    } catch {
      case e: Exception =>
        log.debug("Unable to found file: " + file, e)
        false
    } finally {
      if (input != null) {
        input.close()
      }
    }
  }


  private def lastModified(url: URL): Date = Date.from(Instant.ofEpochMilli(url.openConnection().getLastModified))
}
