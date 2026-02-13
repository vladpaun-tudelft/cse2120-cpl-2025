ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "local.cpl"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .aggregate(week1)
  .settings(
    name := "cse2120-cpl-2025",
    publish / skip := true
  )

lazy val week1 = (project in file("week1"))
  .settings(
    name := "paret-week1",
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/main"),
    Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/test"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
