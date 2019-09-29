package acsgh.spark.scala

import java.io.File

import acsgh.spark.scala.directives.Directives
import acsgh.spark.scala.files.{StaticClasspathFolderFilter, StaticFilesystemFolderFilter}
import spark.{Filter, Request, Response, Service}

object Spark {
  private[scala] val service: Service = Service.ignite()
}

trait Spark extends Directives{
  protected implicit val service: Service = Spark.service

  def get(url: String)(action: RequestContext => Response): Unit = {
    service.get(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def post(url: String)(action: RequestContext => Response): Unit = {
    service.post(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def put(url: String)(action: RequestContext => Response): Unit = {
    service.put(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def patch(url: String)(action: RequestContext => Response): Unit = {
    service.patch(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def delete(url: String)(action: RequestContext => Response): Unit = {
    service.delete(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def head(url: String)(action: RequestContext => Response): Unit = {
    service.head(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def trace(url: String)(action: RequestContext => Response): Unit = {
    service.trace(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def connect(url: String)(action: RequestContext => Response): Unit = {
    service.connect(url, (request, response) => {
      action(toContext(url, request, response))
      ""
    })
  }

  def options(url: String)(action: RequestContext => Response): Unit = {
    service.options(url, (request, response) => {
      action(toContext(url, request, response))
      ""
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


  protected def toContext(url: String, request: spark.Request, response: spark.Response): RequestContext = RequestContext(
    url,
    request,
    response
  )
}
