package acsgh.spark.scala.examples.jetty

import java.util.concurrent.atomic.AtomicLong

import acsgh.spark.scala.ResponseStatus._
import acsgh.spark.scala.{ResponseStatus, SparkApp}
import acsgh.spark.scala.converter.json.jackson.JacksonSparkApp
import acsgh.spark.scala.converter.template.thymeleaf.ThymeleafSparkApp
import acsgh.spark.scala.handler.LoggingEventListener
import acsgh.spark.scala.support.swagger.SwaggerRoutes
import io.swagger.v3.oas.models.OpenAPI


object Boot extends SparkApp with ThymeleafSparkApp with JacksonSparkApp with SwaggerRoutes {
  override val name: String = "Jetty Boot Example"

  override protected val prefix: String = "/templates/"

  implicit protected val openApi: OpenAPI = OpenAPI(
    info = Info(
      title = "Jetty Example",
      description = "Jetty example rest api example",
      version = "1.0",
      contact = Contact(
        email = "dummy@asd.com"
      )
    ),
  )

  addHandler(new LoggingEventListener(spark))

  swaggerRoutes()

  val ids = new AtomicLong(0)

  private var persons: Map[Long, Person] = {
    (0 until 10).map { _ =>
      val id = ids.addAndGet(1)
      (id, Person(id, "John Doe " + id, 2 * id))
    }.toMap
  }

  get("/version") { implicit context =>
    responseBody("Hello!")
  }

  resourceFolder("/*", "public")
  webjars()

  get("/") { implicit context =>
    requestQuery("name".default("Jonh Doe")) { name =>
      thymeleafTemplate("index", Map("name" -> name))
    }
  }

  get("/persons", Operation(
    operationId = "findAll",
    summary = "Get All",
    description = "Get all persons of the service",
    responses = ApiResponses(
      OK -> ApiResponseJson(classOf[List[Person]], "All persons"),
    )
  )) { implicit context =>
    responseJson(persons.values.toList.sortBy(_.id))
  }

    post("/persons", Operation(
      operationId = "createPerson",
      summary = "Create person",
      description = "Get all persons of the service",
      requestBody = RequestBodyJson(
        classOf[Person],
        example = Person(
          123,
          "Alberto",
          32
        )
      ),
      responses = ApiResponses(
        CREATED -> ApiResponseJson(classOf[Person], "The person created"),
        BAD_REQUEST -> ApiResponse("Invalid request")
      )
    )) { implicit context =>
      requestJson(classOf[Person]) { person =>
        val personWithId = person.copy(id = ids.addAndGet(1))
        persons = persons + (personWithId.id -> personWithId)

        responseStatus(ResponseStatus.CREATED) {
          responseJson(personWithId)
        }
      }
    }

    put("/persons/:id", Operation(
      operationId = "editPerson",
      summary = "Edit person",
      parameters = List(
        PathParameter("id", "The person id")
      ),
      requestBody = RequestBodyJson(
        classOf[Person],
        example = Person(
          123,
          "Alberto",
          32
        )
      ),
      responses = ApiResponses(
        CREATED -> ApiResponseJson(classOf[Person], "The person modified"),
        NO_CONTENT -> ApiResponse("Person not found"),
        BAD_REQUEST -> ApiResponse("Invalid request")
      )
    )) { implicit context =>
      requestParam("id".as[Long]) { id =>
        requestJson(classOf[Person]) { personNew =>
          persons.get(id).fold(halt(ResponseStatus.NO_CONTENT)) { personOld =>
            val result = personNew.copy(id = id)
            persons = persons + (result.id -> result)
            responseJson(result)
          }
        }
      }
    }

    get("/persons/:id", Operation(
      operationId = "getPerson",
      summary = "Get person",
      parameters = List(
        PathParameter("id", "The person id")
      ),
      responses = ApiResponses(
        OK -> ApiResponseJson(classOf[Person], "The person"),
        NO_CONTENT -> ApiResponse("Person not found"),
        BAD_REQUEST -> ApiResponse("Invalid request")
      )
    )) { implicit context =>
      requestParam("id".as[Long]) { id =>
        persons.get(id).fold(halt(ResponseStatus.NO_CONTENT)) { personOld =>
          responseJson(personOld)
        }
      }
    }

    delete("/persons/:id", Operation(
      operationId = "deletePerson",
      summary = "Delete person",
      parameters = List(
        PathParameter("id", "The person id")
      ),
      responses = ApiResponses(
        OK -> ApiResponseJson(classOf[Person], "The person deleted"),
        NO_CONTENT -> ApiResponse("Person not found"),
        BAD_REQUEST -> ApiResponse("Invalid request")
      )
    )) { implicit context =>
      requestParam("id".as[Long]) { id =>
        persons.get(id).fold(halt(ResponseStatus.NO_CONTENT)) { personOld =>
          persons = persons - id
          responseJson(personOld)
        }
      }
    }

  //  ws("/echo") { implicit context =>
  //    requestString { input =>
  //      responseBody(s"You said: $input")
  //    }
  //  }
}
