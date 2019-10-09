package acsgh.spark.scala.sparkParts

import java.io.File

import acsgh.spark.scala.RequestContext
import acsgh.spark.scala.files.{StaticClasspathFolderFilter, StaticFilesystemFolderFilter}
import spark.{ExceptionHandler => _, _}

trait SparkFilter extends SparkBase {

  def beforeAll(action: RequestContext => Response): Unit = {
    service.before(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def afterAll(action: RequestContext => Response): Unit = {
    service.after(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def afterAfterAll(action: RequestContext => Response): Unit = {
    service.afterAfter(new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(request, response))
      }
    })
  }

  def before(url: String)(action: RequestContext => Response): Unit = {
    service.before(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def after(url: String)(action: RequestContext => Response): Unit = {
    service.after(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def afterAfter(url: String)(action: RequestContext => Response): Unit = {
    service.afterAfter(url, new Filter {
      override def handle(request: Request, response: Response): Unit = {
        action(toContext(url, request, response))
      }
    })
  }

  def resourceFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticClasspathFolderFilter(resourceFolderPath).handle)

  def filesystemFolder(uri: String, resourceFolderPath: String): Unit = before(uri)(StaticFilesystemFolderFilter(new File(resourceFolderPath)).handle)

  def webjars(): Unit = resourceFolder("/webjars/*", "META-INF/resources/webjars")
}
