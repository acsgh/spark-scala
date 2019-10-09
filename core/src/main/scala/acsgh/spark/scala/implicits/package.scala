package acsgh.spark.scala

import java.net.URI
import java.util

import spark.Request

package object implicits {

  private val START_TIME = "START_TIME"

  implicit class EnumerationConverter[T](input: util.Enumeration[T]) {
    def toList: List[T] = {
      var result = List[T]()

      while (input.hasMoreElements) {
        result = input.nextElement() :: result
      }

      result.reverse
    }
  }

  implicit class RequestEnhancer(input: Request) {
    def getURI: URI = URI.create(input.uri())

    def saveStartTime(): Unit = input.attribute(START_TIME, System.currentTimeMillis)

    def timeSpent: Long = {
      val startTime: Long = input.attribute(START_TIME)
      System.currentTimeMillis - startTime
    }
  }

}
