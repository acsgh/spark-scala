package acsgh.spark.scala.directives

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.convertions.{DefaultFormats, DefaultParamHandling}
import spark.Response

trait RequestCookieDirectives extends DefaultParamHandling with DefaultFormats {

  def requestCookie[P1, R1](param1: Param[P1, R1])(action: R1 => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2](param1: Param[P1, R1], param2: Param[P2, R2])(action: (R1, R2) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3])(action: (R1, R2, R3) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4])(action: (R1, R2, R3, R4) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5])(action: (R1, R2, R3, R4, R5) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6])(action: (R1, R2, R3, R4, R5, R6) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7])(action: (R1, R2, R3, R4, R5, R6, R7) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8])(action: (R1, R2, R3, R4, R5, R6, R7, R8) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10, P11, R11](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10], param11: Param[P11, R11])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue, param11.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10, P11, R11, P12, R12](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10], param11: Param[P11, R11], param12: Param[P12, R12])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue, param11.cookieValue, param12.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10, P11, R11, P12, R12, P13, R13](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10], param11: Param[P11, R11], param12: Param[P12, R12], param13: Param[P13, R13])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue, param11.cookieValue, param12.cookieValue, param13.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10, P11, R11, P12, R12, P13, R13, P14, R14](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10], param11: Param[P11, R11], param12: Param[P12, R12], param13: Param[P13, R13], param14: Param[P14, R14])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue, param11.cookieValue, param12.cookieValue, param13.cookieValue, param14.cookieValue)
  }

  def requestCookie[P1, R1, P2, R2, P3, R3, P4, R4, P5, R5, P6, R6, P7, R7, P8, R8, P9, R9, P10, R10, P11, R11, P12, R12, P13, R13, P14, R14, P15, R15](param1: Param[P1, R1], param2: Param[P2, R2], param3: Param[P3, R3], param4: Param[P4, R4], param5: Param[P5, R5], param6: Param[P6, R6], param7: Param[P7, R7], param8: Param[P8, R8], param9: Param[P9, R9], param10: Param[P10, R10], param11: Param[P11, R11], param12: Param[P12, R12], param13: Param[P13, R13], param14: Param[P14, R14], param15: Param[P15, R15])(action: (R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15) => Response)(implicit context: RequestContext): Response = {
    action(param1.cookieValue, param2.cookieValue, param3.cookieValue, param4.cookieValue, param5.cookieValue, param6.cookieValue, param7.cookieValue, param8.cookieValue, param9.cookieValue, param10.cookieValue, param11.cookieValue, param12.cookieValue, param13.cookieValue, param14.cookieValue, param15.cookieValue)
  }
}
