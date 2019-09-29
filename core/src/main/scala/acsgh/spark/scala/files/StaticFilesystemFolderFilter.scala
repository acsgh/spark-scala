package acsgh.spark.scala.files

import java.io.{File, FileInputStream, FileNotFoundException}
import java.time.Instant
import java.util.Date

import spark.Service

case class StaticFilesystemFolderFilter(private val baseFolder: File)(implicit protected val service:Service) extends FileFilter {

  if (!baseFolder.exists || !baseFolder.isDirectory) throw new FileNotFoundException("Folder " + baseFolder.getAbsolutePath + " does not exist or is not a folder")

  override def getFileInfo(fileName: String): Option[FileInfo] = {
    val file = new File(baseFolder, fileName)

    if (file.exists && !file.isDirectory) {
      val contentSupplier = () => loadFileContent(file)
      Some(FileInfo(contentType(fileName), calculateEtag(contentSupplier()), lastModified(file), contentSupplier))
    } else {
      None
    }
  }

  private def loadFileContent(file: File) = {
    val input = new FileInputStream(file)
    try {
      bytes(input)
    } catch {
      case e: Exception =>
        log.info("Unable to read file", e)
        new Array[Byte](0)
    } finally {
      if (input != null) {
        input.close()
      }
    }
  }

  private def lastModified(file: File): Date = Date.from(Instant.ofEpochMilli(file.lastModified))
}
