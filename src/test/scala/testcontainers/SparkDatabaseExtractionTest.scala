package com.hypercodelab
package testcontainers

import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.MountableFile


// Modeling the table as a case class
case class ProgrammingLanguange(name: String, version: String)


class SparkDatabaseExtractionTest extends AnyFlatSpec with Matchers {

  val spark = SparkSession.builder().master("local").getOrCreate()
  import spark.implicits._

  
  it should "Extract data from a PostgreSQL database" in {

    val postgresContainer = new PostgreSQLContainer("postgres:16.1")
    postgresContainer.withCopyFileToContainer(MountableFile.forClasspathResource("init-dbt.sql"), "/docker-entrypoint-initdb.d/")
    postgresContainer.start()

    println(postgresContainer.getJdbcUrl)
      val jdbcUrl =  s"${postgresContainer.getJdbcUrl}?user=test&password=test"
      val tableName: String = "programming_list"

      val data: Array[ProgrammingLanguange] = spark
        .read
        .format("jdbc")
        .option("url", jdbcUrl)
        .option("user", postgresContainer.getUsername)
        .option("password", postgresContainer.getPassword)
        .option("dbtable", tableName)
        .load()
        .as[ProgrammingLanguange].collect()

      data should contain allOf (
        ProgrammingLanguange("Python", "3.12"),
        ProgrammingLanguange("Scala", "2.12")
      )

      postgresContainer.stop()
    }

}
