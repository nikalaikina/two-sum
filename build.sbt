ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "parimatch",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.3.5",
      "org.http4s" %% "http4s-dsl" % "1.0.0-M31",
      "org.http4s" %% "http4s-circe" % "1.0.0-M31",
      "org.http4s" %% "http4s-blaze-client" % "1.0.0-M31",
      "org.http4s" %% "http4s-blaze-server" % "1.0.0-M31",
      "io.circe" %% "circe-generic" % "0.15.0-M1",
      "org.scalacheck" %% "scalacheck" % "1.15.4" % "test",
      "org.scalatest" %% "scalatest" % "3.2.11" % "test"
    )
  )
