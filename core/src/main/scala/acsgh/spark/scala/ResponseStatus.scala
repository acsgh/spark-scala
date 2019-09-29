package acsgh.spark.scala

import enumeratum._

import scala.collection.immutable

sealed abstract class ResponseStatus
(
  val code: Int,
  val message: String
) extends EnumEntry

object ResponseStatus extends Enum[ResponseStatus] {
  val values: immutable.IndexedSeq[ResponseStatus] = findValues

  case object CONTINUE extends ResponseStatus(100, "Continue")

  case object SWITCHING_PROTOCOLS extends ResponseStatus(101, "Switching Protocols")

  case object PROCESSING extends ResponseStatus(102, "Processing")

  case object OK extends ResponseStatus(200, "OK")

  case object CREATED extends ResponseStatus(201, "Created")

  case object ACCEPTED extends ResponseStatus(202, "Accepted")

  case object NON_AUTHORITATIVE_INFORMATION extends ResponseStatus(203, "Non-Authoritative Information")

  case object NO_CONTENT extends ResponseStatus(204, "No Content")

  case object RESET_CONTENT extends ResponseStatus(205, "Reset Content")

  case object PARTIAL_CONTENT extends ResponseStatus(206, "Partial Content")

  case object MULTI_STATUS extends ResponseStatus(207, "Multi-Status")

  case object MULTIPLE_CHOICES extends ResponseStatus(300, "Multiple Choices")

  case object MOVED_PERMANENTLY extends ResponseStatus(301, "Moved Permanently")

  case object FOUND extends ResponseStatus(302, "Found")

  case object SEE_OTHER extends ResponseStatus(303, "See Other")

  case object NOT_MODIFIED extends ResponseStatus(304, "Not Modified")

  case object USE_PROXY extends ResponseStatus(305, "Use Proxy")

  case object TEMPORARY_REDIRECT extends ResponseStatus(307, "Temporary Redirect")

  case object PERMANENT_REDIRECT extends ResponseStatus(308, "Permanent Redirect")

  case object BAD_REQUEST extends ResponseStatus(400, "Bad Request")

  case object UNAUTHORIZED extends ResponseStatus(401, "Unauthorized")

  case object PAYMENT_REQUIRED extends ResponseStatus(402, "Payment Required")

  case object FORBIDDEN extends ResponseStatus(403, "Forbidden")

  case object NOT_FOUND extends ResponseStatus(404, "Not Found")

  case object METHOD_NOT_ALLOWED extends ResponseStatus(405, "Method Not Allowed")

  case object NOT_ACCEPTABLE extends ResponseStatus(406, "Not Acceptable")

  case object PROXY_AUTHENTICATION_REQUIRED extends ResponseStatus(407, "Proxy Authentication Required")

  case object REQUEST_TIMEOUT extends ResponseStatus(408, "Request Timeout")

  case object CONFLICT extends ResponseStatus(409, "Conflict")

  case object GONE extends ResponseStatus(410, "Gone")

  case object LENGTH_REQUIRED extends ResponseStatus(411, "Length Required")

  case object PRECONDITION_FAILED extends ResponseStatus(412, "Precondition Failed")

  case object I_AM_A_TEAPOT extends ResponseStatus(418, "I'am a teapot")

  case object REQUEST_ENTITY_TOO_LARGE extends ResponseStatus(413, "Request Entity Too Large")

  case object REQUEST_URI_TOO_LONG extends ResponseStatus(414, "Request-URI Too Long")

  case object UNSUPPORTED_MEDIA_TYPE extends ResponseStatus(415, "Unsupported Media Type")

  case object REQUESTED_RANGE_NOT_SATISFIABLE extends ResponseStatus(416, "Requested Range Not Satisfiable")

  case object EXPECTATION_FAILED extends ResponseStatus(417, "Expectation Failed")

  case object MISDIRECTED_REQUEST extends ResponseStatus(421, "Misdirected Request")

  case object UNPROCESSABLE_ENTITY extends ResponseStatus(422, "Unprocessable Entity")

  case object LOCKED extends ResponseStatus(423, "Locked")

  case object FAILED_DEPENDENCY extends ResponseStatus(424, "Failed Dependency")

  case object UNORDERED_COLLECTION extends ResponseStatus(425, "Unordered Collection")

  case object UPGRADE_REQUIRED extends ResponseStatus(426, "Upgrade Required")

  case object PRECONDITION_REQUIRED extends ResponseStatus(428, "Precondition Required")

  case object TOO_MANY_REQUESTS extends ResponseStatus(429, "Too Many Requests")

  case object REQUEST_HEADER_FIELDS_TOO_LARGE extends ResponseStatus(431, "Request Header Fields Too Large")

  case object INTERNAL_SERVER_ERROR extends ResponseStatus(500, "Internal Server Error")

  case object NOT_IMPLEMENTED extends ResponseStatus(501, "Not Implemented")

  case object BAD_GATEWAY extends ResponseStatus(502, "Bad Gateway")

  case object SERVICE_UNAVAILABLE extends ResponseStatus(503, "Service Unavailable")

  case object GATEWAY_TIMEOUT extends ResponseStatus(504, "Gateway Timeout")

  case object HTTP_VERSION_NOT_SUPPORTED extends ResponseStatus(505, "HTTP Version Not Supported")

  case object VARIANT_ALSO_NEGOTIATES extends ResponseStatus(506, "Variant Also Negotiates")

  case object INSUFFICIENT_STORAGE extends ResponseStatus(507, "Insufficient Storage")

  case object NOT_EXTENDED extends ResponseStatus(510, "Not Extended")

  case object NETWORK_AUTHENTICATION_REQUIRED extends ResponseStatus(511, "Network Authentication Required")


}



