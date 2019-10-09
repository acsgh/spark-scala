package acsgh.spark.scala.files

import  acsgh.spark.scala.implicits._
import java.io.{ByteArrayOutputStream, InputStream}
import java.math.BigInteger
import java.net.{URI, URLConnection}
import java.security.MessageDigest
import java.text.SimpleDateFormat

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.ResponseStatus._
import acsgh.spark.scala.directives.Directives
import com.acsgh.common.scala.log.LogSupport
import spark.Response


object FileFilter {
  val DATE_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
}

abstract class FileFilter extends Directives with LogSupport {

  def handle(context: RequestContext): Response = {
    implicit val ctx: RequestContext = context
    val requestUri = uri(context)
    log.trace("Requesting file: {}", requestUri)

    getFileInfo(requestUri).fold(context.response) { fileInfo =>
      responseHeader("Content-Type", fileInfo.contentType) {
        responseHeader("ETag", fileInfo.etag) {
          responseHeader("Last-Modified", FileFilter.DATE_FORMATTER.format(fileInfo.lastModified)) {

            requestHeader("If-None-Match".opt, "If-Modified-Since".opt) { (ifNoneMatchHeader, ifModifiedSinceHeader) =>
              if (fileInfo.isModified(ifNoneMatchHeader, ifModifiedSinceHeader)) {
                responseStatus(OK) {
                  responseBody(fileInfo.content)
                  service.halt()
                  context.response
                }
              } else {
                error(NOT_MODIFIED)
              }
            }
          }
        }
      }
    }
  }

  protected def getFileInfo(uri: String): Option[FileInfo]

  protected def addTradingSlash(uri: String): String = if (!uri.startsWith("/")) s"/$uri" else uri

  protected def removeTradingSlash(uri: String): String = if ((uri.length > 1) && uri.startsWith("/")) uri.substring(1) else uri

  protected def removeEndingSlash(uri: String): String = if ((uri.length > 1) && uri.endsWith("/")) uri.substring(0, uri.length - 1) else uri

  protected def calculateEtag(bytes: Array[Byte]): String = {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(bytes)
    val bigInt = new BigInteger(1, digest)
    var result = bigInt.toString(16).toUpperCase

    while (result.length < 32) {
      result = "0" + result
    }
    result
  }

  protected def contentType(filename: String): String = {
    var result = URLConnection.guessContentTypeFromName(filename)
    if (filename.endsWith(".js")) result = "application/javascript"
    else if (filename.endsWith(".css")) result = "text/css"
    if (result == null) result = "text/html"
    result
  }

  protected def bytes(input: InputStream): Array[Byte] = {
    val output = new ByteArrayOutputStream()

    try {
      val buffer = new Array[Byte](1024)

      Stream.continually(input.read(buffer))
        .takeWhile(_ != -1)
        .foreach(output.write(buffer, 0, _))

      output.toByteArray
    } finally {
      output.close()
    }
  }


  private def uri(context: RequestContext) = Option(context.request.splat()).filter(_.nonEmpty).map(_ (0)).map(addTradingSlash).map(removeEndingSlash).getOrElse(context.request.getURI.getPath)
}
