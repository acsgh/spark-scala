package acsgh.spark.scala.support.swagger.dsl

import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.core.util.Json
import io.swagger.v3.oas.models._
import io.swagger.v3.oas.models.callbacks.Callback
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.info._
import io.swagger.v3.oas.models.links.Link
import io.swagger.v3.oas.models.media.Encoding.StyleEnum
import io.swagger.v3.oas.models.media.{Content, Encoding, MediaType, Schema, StringSchema}
import io.swagger.v3.oas.models.parameters.{RequestBody, Parameter => SwaggerParameter}
import io.swagger.v3.oas.models.responses.{ApiResponse, ApiResponses}
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.{Server, ServerVariable, ServerVariables}
import io.swagger.v3.oas.models.tags.Tag

import scala.collection.JavaConverters._

object OpenApiBuilderDefault extends OpenApiBuilder {

  private val converter = {
    ScalaSwaggerSupport.register()
    ModelConverters.getInstance()
  }

  def read(clazz: Class[_]): Map[String, Schema[_]] = Map() ++ converter.readAll(clazz).asScala
}

trait OpenApiBuilder {

  def OpenAPI
  (
    openapi: String = "3.0.1",
    info: Info = null,
    externalDocs: ExternalDocumentation = null,
    servers: List[Server] = null,
    security: List[SecurityRequirement] = null,
    tags: List[Tag] = null,
    paths: Paths = null,
    components: Components = null,
    extensions: Map[String, AnyRef] = null
  ): OpenAPI = {
    val result = new OpenAPI()
    result.openapi(openapi)
    result.info(info)
    result.externalDocs(externalDocs)
    result.servers(servers.asJava)
    result.security(security.asJava)
    result.tags(tags.asJava)
    result.paths(paths)
    result.components(components)
    result.extensions(extensions.asJava)
    result
  }

  def Info
  (
    title: String = null,
    description: String = null,
    termsOfService: String = null,
    contact: Contact = null,
    license: License = null,
    version: String = null,
    extensions: Map[String, AnyRef] = null
  ): Info = {
    val result = new Info()
    result.title(title)
    result.description(description)
    result.termsOfService(termsOfService)
    result.contact(contact)
    result.license(license)
    result.version(version)
    result.setExtensions(extensions.asJava)
    result
  }

  def Contact
  (
    name: String = null,
    url: String = null,
    email: String = null,
    extensions: Map[String, AnyRef] = null
  ): Contact = {
    val result = new Contact()
    result.name(name)
    result.url(url)
    result.email(email)
    result.setExtensions(extensions.asJava)
    result
  }

  def License
  (
    name: String = null,
    url: String = null,
    email: String = null,
    extensions: Map[String, AnyRef] = null
  ): License = {
    val result = new License()
    result.name(name)
    result.url(url)
    result.setExtensions(extensions.asJava)
    result
  }

  def Server
  (
    url: String,
    description: String = null,
    variables: ServerVariables = null,
    extensions: Map[String, AnyRef] = null
  ): Server = {
    val result = new Server()
    result.url(url)
    result.description(description)
    result.variables(variables)
    result.setExtensions(extensions.asJava)
    result
  }

  def ServerVariables
  (
    values: Map[String, ServerVariable],
    extensions: Map[String, AnyRef] = null
  ): ServerVariables = {
    val result = new ServerVariables()
    result.putAll(values.asJava)
    result.setExtensions(extensions.asJava)
    result
  }

  def ServerVariable
  (
    enum: Set[String] = null,
    default: String = null,
    description: String = null,
    variables: ServerVariables = null,
    extensions: Map[String, AnyRef] = null
  ): ServerVariable = {
    val result = new ServerVariable()
    result._enum(enum.toList.asJava)
    result._default(default)
    result.description(description)
    result.setExtensions(extensions.asJava)
    result
  }

  def ExternalDocumentation
  (
    description: String = null,
    url: String = null,
    extensions: Map[String, AnyRef] = null
  ): ExternalDocumentation = {
    val result = new ExternalDocumentation()
    result.description(description)
    result.url(url)
    result.setExtensions(extensions.asJava)
    result
  }

  def Tag
  (
    name: String,
    description: String = null,
    url: String = null,
    externalDocs: ExternalDocumentation = null,
    extensions: Map[String, AnyRef] = null
  ): Tag = {
    val result = new Tag()
    result.name(name)
    result.description(description)
    result.externalDocs(externalDocs)
    result.setExtensions(extensions.asJava)
    result
  }

  def SecurityRequirement
  (
    values: Map[String, List[String]] = null,
  ): SecurityRequirement = {
    val result = new SecurityRequirement()
    result.putAll(values.mapValues(_.asJava).asJava)
    result
  }

  def Operation
  (
    tags: List[String] = null,
    summary: String = null,
    description: String = null,
    externalDocs: ExternalDocumentation = null,
    operationId: String = null,
    parameters: List[SwaggerParameter] = null,
    requestBody: RequestBody = null,
    responses: ApiResponses = new ApiResponses,
    callbacks: Map[String, Callback] = null,
    deprecated: java.lang.Boolean = null,
    security: List[SecurityRequirement] = null,
    servers: List[Server] = null,
    extensions: Map[String, AnyRef] = null
  ): Operation = {
    val result = new Operation()
    result.tags(tags.asJava)
    result.summary(summary)
    result.description(description)
    result.externalDocs(externalDocs)
    result.operationId(operationId)
    result.parameters(parameters.asJava)
    result.requestBody(requestBody)
    result.responses(responses)
    result.callbacks(callbacks.asJava)
    result.deprecated(deprecated)
    result.security(security.asJava)
    result.servers(servers.asJava)
    result.extensions(extensions.asJava)
    result
  }

  def ApiResponses
  (
    responses: (Int, ApiResponse)*
  ): ApiResponses = {
    val result = new ApiResponses()

    if (responses != null) {
      responses.foreach(e => result.put(e._1.toString, e._2))
    }

    result
  }

  def RequestBody
  (
    description: String = null,
    content: Content = null,
    required: java.lang.Boolean = null,
    extensions: Map[String, AnyRef] = null,
    $ref: String = null
  ): RequestBody = {
    val result = new RequestBody()

    result.description(description)
    result.content(content)
    result.required(required)
    result.extensions(extensions.asJava)
    result.$ref($ref)

    result
  }

  def ApiResponse
  (
    description: String = null,
    headers: Map[String, Header] = null,
    content: Content = null,
    links: Map[String, Link] = null,
    extensions: Map[String, AnyRef] = null,
    $ref: String = null
  ): ApiResponse = {
    val result = new ApiResponse()

    result.description(description)
    result.headers(headers.asJava)
    result.content(content)
    result.setLinks(links.asJava)
    result.extensions(extensions.asJava)
    result.$ref($ref)

    result
  }

  def Content
  (
    values: Map[String, MediaType]
  ): Content = {
    val result = new Content()

    result.putAll(values.asJava)

    result
  }

  def MediaType
  (
    schema: Schema[_] = null,
    examples: Map[String, Example] = null,
    example: Object = null,
    encoding: Map[String, Encoding] = null,
    extensions: Map[String, Object] = null,
  ): MediaType = {
    val result = new MediaType()

    result.schema(schema)
    result.examples(examples.asJava)
    result.example(example)
    result.encoding(encoding.asJava)
    result.extensions(extensions.asJava)

    result
  }

  def Encoding
  (
    contentType: String = "application/json",
    headers: Map[String, Header],
    style: StyleEnum,
    explode: java.lang.Boolean,
    allowReserved: java.lang.Boolean,
    extensions: Map[String, Object] = null
  ): Encoding = {
    val result = new Encoding()

    result.contentType(contentType)
    result.headers(headers.asJava)
    result.style(style)
    result.explode(explode)
    result.allowReserved(allowReserved)
    result.extensions(extensions.asJava)

    result
  }

  def Example
  (
    value: Object,
    summary: String = null,
    description: String = null,
    externalValue: String = null,
    $ref: String = null,
    extensions: Map[String, Object] = null
  ): Example = {
    val result = new Example()

    result.summary(summary)
    result.description(description)
    result.value(value)
    result.externalValue(externalValue)
    result.$ref($ref)
    result.extensions(extensions.asJava)

    result
  }

  def Parameter
  (
    name: String,
    in: String = null,
    description: String = null,
    required: java.lang.Boolean = null,
    deprecated: java.lang.Boolean = null,
    allowEmptyValue: java.lang.Boolean = null,
    $ref: String = null,
    style: SwaggerParameter.StyleEnum = null,
    explode: java.lang.Boolean = null,
    allowReserved: java.lang.Boolean = null,
    schema: Schema[_] = new StringSchema,
    examples: Map[String, Example] = null,
    example: AnyRef = null,
    content: Content = null,
    extensions: Map[String, AnyRef] = null,
  ): SwaggerParameter = {
    val result = new SwaggerParameter()

    result.name(name)
    result.in(in)
    result.description(description)
    result.required(required)
    result.deprecated(deprecated)
    result.allowEmptyValue(allowEmptyValue)
    result.$ref($ref)
    result.style(style)
    result.explode(explode)
    result.allowReserved(allowReserved)
    result.schema(schema)
    result.examples(examples.asJava)
    result.example(example)
    result.content(content)
    result.extensions(extensions.asJava)

    result
  }

  def PathParameter
  (
    name: String,
    description: String = null,
    deprecated: java.lang.Boolean = null,
    allowEmptyValue: java.lang.Boolean = null,
    $ref: String = null,
    style: SwaggerParameter.StyleEnum = null,
    explode: java.lang.Boolean = null,
    allowReserved: java.lang.Boolean = null,
    schema: Schema[_]  = new StringSchema,
    examples: Map[String, Example] = null,
    example: AnyRef = null,
    content: Content = null,
    extensions: Map[String, AnyRef] = null,
  ): SwaggerParameter = Parameter(
    name,
    "path",
    description,
    true,
    deprecated,
    allowEmptyValue,
    $ref,
    style,
    explode,
    allowReserved,
    schema,
    examples,
    example,
    content,
    extensions
  )

  def QueryParameter
  (
    name: String,
    description: String = null,
    required: java.lang.Boolean = null,
    deprecated: java.lang.Boolean = null,
    allowEmptyValue: java.lang.Boolean = null,
    $ref: String = null,
    style: SwaggerParameter.StyleEnum = null,
    explode: java.lang.Boolean = null,
    allowReserved: java.lang.Boolean = null,
    schema: Schema[_]  = new StringSchema,
    examples: Map[String, Example] = null,
    example: AnyRef = null,
    content: Content = null,
    extensions: Map[String, AnyRef] = null,
  ): SwaggerParameter = Parameter(
    name,
    "query",
    description,
    required,
    deprecated,
    allowEmptyValue,
    $ref,
    style,
    explode,
    allowReserved,
    schema,
    examples,
    example,
    content,
    extensions
  )

  def HeaderParameter
  (
    name: String,
    description: String = null,
    required: java.lang.Boolean = null,
    deprecated: java.lang.Boolean = null,
    allowEmptyValue: java.lang.Boolean = null,
    $ref: String = null,
    style: SwaggerParameter.StyleEnum = null,
    explode: java.lang.Boolean = null,
    allowReserved: java.lang.Boolean = null,
    schema: Schema[_]  = new StringSchema,
    examples: Map[String, Example] = null,
    example: AnyRef = null,
    content: Content = null,
    extensions: Map[String, AnyRef] = null,
  ): SwaggerParameter = Parameter(
    name,
    "header",
    description,
    required,
    deprecated,
    allowEmptyValue,
    $ref,
    style,
    explode,
    allowReserved,
    schema,
    examples,
    example,
    content,
    extensions
  )

  def CookieParameter
  (
    name: String,
    description: String = null,
    required: java.lang.Boolean = null,
    deprecated: java.lang.Boolean = null,
    allowEmptyValue: java.lang.Boolean = null,
    $ref: String = null,
    style: SwaggerParameter.StyleEnum = null,
    explode: java.lang.Boolean = null,
    allowReserved: java.lang.Boolean = null,
    schema: Schema[_]  = new StringSchema,
    examples: Map[String, Example] = null,
    example: AnyRef = null,
    content: Content = null,
    extensions: Map[String, AnyRef] = null,
  ): SwaggerParameter = Parameter(
    name,
    "cookie",
    description,
    required,
    deprecated,
    allowEmptyValue,
    $ref,
    style,
    explode,
    allowReserved,
    schema,
    examples,
    example,
    content,
    extensions
  )


  def RequestBodyJson[T <: AnyRef]
  (
    contentClass: Class[T],
    description: String = null,
    required: Boolean = true,
    example: T = null,
  )(implicit openApi: OpenAPI): RequestBody = {
    val result = new RequestBody()

    result.description(description)
    result.required(required)

    addSchemas(contentClass)
    result.content(ContentJson(contentClass, example))
    result
  }


  def ApiResponseJson[T <: AnyRef]
  (
    contentClass: Class[T],
    description: String = null,
    example: T = null
  )(implicit openApi: OpenAPI): ApiResponse = {
    val result = new ApiResponse()
    result.description(description)
    result.content(ContentJson(contentClass, example))
    result
  }

  def ContentJson[T <: AnyRef]
  (
    contentClass: Class[T],
    example: T = null
  )(implicit openApi: OpenAPI): Content = {
    addSchemas(contentClass)

    val examples = if (example != null) {
      Map(
        "default" -> Example(
          Json.pretty().writeValueAsString(example)
        )
      )
    } else {
      null
    }

    Content(
      Map(
        "application/json" -> MediaType(
          schema = new Schema[T]().$ref(contentClass.getSimpleName),
          examples = examples
        )
      )
    )
  }


  private def addSchemas(contentClass: Class[_])(implicit openApi: OpenAPI): Unit = {
    val schemas = OpenApiBuilderDefault.read(contentClass)

    if (openApi.getComponents == null) {
      openApi.setComponents(new Components)
    }

    schemas.foreach(e => openApi.getComponents.addSchemas(e._1, e._2))
  }
}
