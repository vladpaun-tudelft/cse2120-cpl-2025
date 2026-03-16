ThisBuild / scalaVersion := "3.3.1"
ThisBuild / organization := "local.cpl"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .aggregate(week1, week3, week4, week5, week6)
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

lazy val week3 = (project in file("week3"))
  .settings(
    name := "paret-week3",
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/main"),
    Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/test"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )

lazy val week4 = (project in file("week4"))
  .settings(
    name := "paret-week4",
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/main"),
    Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/test"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )

lazy val week5 = (project in file("week5"))
  .settings(
    name := "paret-week5",
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/main"),
    Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/test"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )

lazy val week6 = (project in file("week6"))
  .settings(
    name := "paret-week6",
    Compile / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/main"),
    Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "src/test"),
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
  )
