package acsgh.spark.scala

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.handler.{ErrorCodeHandler, EventListener, ExceptionHandler}
import com.acsgh.common.scala.App
import spark.{Response, Service}

trait SparkDelegate extends Directives {

  protected val spark:Spark

  def productionMode(value: Boolean): Unit = spark.productionMode(value)

  def productionMode: Boolean = spark.productionMode

  def activeThreadCount(): Int = spark.activeThreadCount()

  def threadPool(maxThreads: Int, minThreads: Int, idleTimeoutMillis: Int): Service = spark.threadPool(maxThreads, minThreads, idleTimeoutMillis)

  def threadPool(maxThreads: Int): Service = spark.threadPool(maxThreads)

  def port: Int = spark.port

  def port(port: Int): Service = spark.port(port)

  def ipAddress(ipAddress: String): Service = spark.ipAddress(ipAddress)

  def exceptionHandler(handler: ExceptionHandler): Unit = spark.exceptionHandler(handler)

  def addHandler(listener: EventListener): Unit = spark.addHandler(listener)

  def removeHandler(listener: EventListener): Unit = spark.removeHandler(listener)

  def errorPageHandler(responseStatus: ResponseStatus, handler: ErrorCodeHandler): Unit = spark.errorPageHandler(responseStatus, handler)

  def before(url: String)(action: RequestContext => Response): Unit = spark.before(url)(action)

  def after(url: String)(action: RequestContext => Response): Unit = spark.after(url)(action)

  def afterAfter(url: String)(action: RequestContext => Response): Unit = spark.afterAfter(url)(action)

  def resourceFolder(uri: String, resourceFolderPath: String): Unit = spark.resourceFolder(uri, resourceFolderPath)

  def filesystemFolder(uri: String, resourceFolderPath: String): Unit = spark.filesystemFolder(uri, resourceFolderPath)

  def webjars(): Unit = spark.webjars()

  def get(url: String)(action: RequestContext => Response): Unit = spark.get(url)(action)

  def post(url: String)(action: RequestContext => Response): Unit = spark.post(url)(action)

  def put(url: String)(action: RequestContext => Response): Unit = spark.put(url)(action)

  def patch(url: String)(action: RequestContext => Response): Unit = spark.patch(url)(action)

  def delete(url: String)(action: RequestContext => Response): Unit = spark.delete(url)(action)

  def head(url: String)(action: RequestContext => Response): Unit = spark.head(url)(action)

  def trace(url: String)(action: RequestContext => Response): Unit = spark.trace(url)(action)

  def connect(url: String)(action: RequestContext => Response): Unit = spark.connect(url)(action)

  def options(url: String)(action: RequestContext => Response): Unit = spark.options(url)(action)
}
