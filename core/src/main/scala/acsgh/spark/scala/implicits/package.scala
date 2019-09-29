package acsgh.spark.scala

import java.util

package object implicits {
  implicit class EnumerationConverter[T](input: util.Enumeration[T]) {
    def toList: List[T] = {
      var result = List[T]()

      while(input.hasMoreElements){
        result = input.nextElement() :: result
      }

      result.reverse
    }
  }
}
