package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.functional

import com.bitdubai.fermat_api.FermatException

/**
 * Created by jorgegonzalez on 2015.06.30..
 */

class ErrorReport(source: String, severity: String, fermatException: FermatException) {

  val reportSeparator = "========================================================================================================================================================\n"
  val headerInfo =   "Source: " + source + "\n" + "Severity: " + severity + "\n"
  val header = reportSeparator + "Fermat Error Manager * Unexpected Exception Report\n" + reportSeparator + headerInfo
  val footer = "Exceptions Processed: " + fermatException.getDepth + "\n" + reportSeparator
  val exceptionSeparator = "********************************************************************************************************************************************************\n"

  def generateReport(): String ={
    return constructExceptionReports(fermatException, footer)
  }

  private def constructExceptionReports(exception : FermatException, acumulator: String): String ={
    if(exception == null)
      return header + acumulator
    else
      return constructExceptionReports(exception.getCause, exceptionSeparator + exception.toString + acumulator)
  }

}
