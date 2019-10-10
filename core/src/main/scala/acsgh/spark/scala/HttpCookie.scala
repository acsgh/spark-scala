package acsgh.spark.scala

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


final case class HttpCookie
(
  name: String,
  value: String,
  expires: Option[LocalDateTime] = None,
  maxAge: Option[Long] = None,
  domain: Option[String] = None,
  path: Option[String] = None,
  secure: Boolean = false,
  httpOnly: Boolean = false
) {
  def toValue: String = {
    var result = s"$name=$value"

    if (expires.isDefined) result = s"$result; Expires=${DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(expires.get)}"
    if (maxAge.isDefined) result = s"$result; Max-Age=${maxAge.get}"
    if (domain.isDefined) result = s"$result; Domain=${domain.get}"
    if (path.isDefined) result = s"$result; Path=${path.get}"
    if (secure) result = s"$result; Secure"
    if (httpOnly) result = s"$result; HttpOnly"

    result
  }
}
