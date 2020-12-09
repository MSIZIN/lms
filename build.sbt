import _root_.sbt.Keys._

name := """lms"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

scalacOptions := List(
  "-encoding",
  "utf8",
  "-feature",
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8",
  "-language:_",
  "-Ymacro-annotations"
)

coverageEnabled in (Test, compile) := true

libraryDependencies ++= Seq(
  guice,
  jdbc,
  "org.postgresql" % "postgresql" % "42.2.10",
  "org.playframework.anorm" %% "anorm" % "2.6.5",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)
