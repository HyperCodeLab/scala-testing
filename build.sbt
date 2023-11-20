ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .settings(
    name := "scala-testing"
  )


libraryDependencies ++= Seq(
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.12.2",
  // Spark Libraries
  "org.apache.spark" %% "spark-core" % "3.4.0",
  "org.apache.spark" %% "spark-sql" % "3.4.0",

  "org.postgresql" % "postgresql" % "42.6.0",

  // Testing libraries
  "org.scalatest" %% "scalatest" % "3.2.17" % "test",

  // TestContainer
  "org.testcontainers" % "postgresql" % "1.18.0" % "test"
)

dependencyOverrides ++= Seq(

)