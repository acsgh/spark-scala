package acsgh.spark.scala

import enumeratum._

sealed abstract class RedirectStatus
(
  val status: ResponseStatus
) extends EnumEntry

object RedirectStatus extends Enum[RedirectStatus] {
  val values = findValues

  case object MOVED_PERMANENTLY extends RedirectStatus(ResponseStatus.MOVED_PERMANENTLY)

  case object FOUND extends RedirectStatus(ResponseStatus.FOUND)

  case object SEE_OTHER extends RedirectStatus(ResponseStatus.SEE_OTHER)

  case object TEMPORARY_REDIRECT extends RedirectStatus(ResponseStatus.TEMPORARY_REDIRECT)

  case object PERMANENT_REDIRECT extends RedirectStatus(ResponseStatus.PERMANENT_REDIRECT)

}



