package acsgh.spark.scala.sparkParts

import acsgh.spark.scala.RequestContext
import spark.{ExceptionHandler => _, _}

trait SparkServlet extends SparkBase {

  def get(url: String)(action: RequestContext => Response): Unit = {
    service.get(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def post(url: String)(action: RequestContext => Response): Unit = {
    service.post(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def put(url: String)(action: RequestContext => Response): Unit = {
    service.put(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def patch(url: String)(action: RequestContext => Response): Unit = {
    service.patch(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def delete(url: String)(action: RequestContext => Response): Unit = {
    service.delete(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def head(url: String)(action: RequestContext => Response): Unit = {
    service.head(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def trace(url: String)(action: RequestContext => Response): Unit = {
    service.trace(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def connect(url: String)(action: RequestContext => Response): Unit = {
    service.connect(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }

  def options(url: String)(action: RequestContext => Response): Unit = {
    service.options(url, (request, response) => {
      action(toContext(request, response))
      ""
    })
  }
}
