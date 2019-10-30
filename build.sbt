ThisBuild / organization := "com.github.acsgh.spark.scala"
ThisBuild / scalaVersion := "2.13.1"

import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

lazy val commonSettings = Seq(
  scalacOptions += "-feature",
  scalacOptions += "-deprecation",
  sonatypeProfileName := "com.github.acsgh",
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),
  crossScalaVersions := List("2.12.10", "2.13.1"),
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  libraryDependencies ++= Seq(
    "com.beachape" %% "enumeratum" % "1.5.13",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "org.slf4j" % "slf4j-api" % "1.7.21",
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.2" % Test,
    "org.pegdown" % "pegdown" % "1.4.2" % Test,
    "org.scalamock" %% "scalamock" % "4.4.0" % Test,
  ),
  homepage := Some(url("https://github.com/acsgh/spark-scala")),
  licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo := sonatypePublishToBundle.value,
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/acsgh/spark-scala"),
      "scm:git:git@github.com:acsgh/spark-scala.git"
    )
  ),
  developers := List(
    Developer("acsgh", "Alberto Crespo", "albertocresposanchez@gmail.com", url("https://github.com/acsgh"))
  )
)


lazy val root = (project in file("."))
  .settings(
    name := "spark-scala",
    commonSettings,
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .aggregate(
    core,
    converterJsonJackson,
    converterJsonSpray,
    converterTemplateFreemarker,
    converterTemplateThymeleaf,
    converterTemplateTwirl,
    supportSwagger,
    examples
  )

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.github.acsgh.common.scala" %% "core" % "1.2.12",
      "com.sparkjava" % "spark-core"  % "2.8.0"
    )
  )

lazy val converterJsonJackson = (project in file("converter/json/jackson"))
  .settings(
    organization := "com.github.acsgh.spark.scala.converter.json",
    name := "jackson",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.9",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9"
    )
  )
  .dependsOn(core)

lazy val converterJsonSpray = (project in file("converter/json/spray"))
  .settings(
    organization := "com.github.acsgh.spark.scala.converter.json",
    name := "spray",
    commonSettings,
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-json" % "1.3.5"
    )
  )
  .dependsOn(core)

lazy val converterTemplateFreemarker = (project in file("converter/template/freemarker"))
  .settings(
    organization := "com.github.acsgh.spark.scala.converter.template",
    name := "freemarker",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.freemarker" % "freemarker" % "2.3.28",
      "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
    )
  )
  .dependsOn(core)

lazy val converterTemplateThymeleaf = (project in file("converter/template/thymeleaf"))
  .settings(
    organization := "com.github.acsgh.spark.scala.converter.template",
    name := "thymeleaf",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.thymeleaf" % "thymeleaf" % "3.0.11.RELEASE",
      "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
    )
  )
  .dependsOn(core)

lazy val converterTemplateTwirl = (project in file("converter/template/twirl"))
  .settings(
    organization := "com.github.acsgh.spark.scala.converter.template",
    name := "twirl",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "twirl-api" % "1.4.2",
      "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
    )
  )
  .dependsOn(core)

lazy val supportSwagger = (project in file("support/swagger"))
  .settings(
    organization := "com.github.acsgh.spark.scala.support",
    name := "swagger",
    commonSettings,
    libraryDependencies ++= Seq(
      "io.swagger.core.v3" % "swagger-core" % "2.0.8",
      "io.swagger.core.v3" % "swagger-annotations" % "2.0.8",
      "io.swagger.core.v3" % "swagger-integration" % "2.0.8",
      "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.0.4",
      "org.webjars" % "swagger-ui" % "3.23.0"
    )
  )
  .dependsOn(core)


lazy val examples = (project in file("examples"))
  .settings(
    organization := "com.github.acsgh.spark.scala",
    name := "examples",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.webjars" % "bootstrap" % "3.3.7-1",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
    )
  )
  .dependsOn(converterTemplateThymeleaf)
  .dependsOn(converterJsonJackson)
  .dependsOn(core)
  .dependsOn(supportSwagger)
