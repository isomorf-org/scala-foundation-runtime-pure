
lazy val `foundation-runtime-pure` = (project in file("."))
  
organization := "org.isomorf"
name         := "foundation-runtime-pure"
version      := "0.1.0"
    
scalaVersion := "2.12.3"
scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint:_", "-Ywarn-unused-import")

libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil
unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil

//useGpg := true

// POM settings for Sonatype
homepage   := Some(url("https://github.com/isomorf-org/scala-foundation-runtime-pure"))
scmInfo    := Some(ScmInfo(url("https://github.com/isomorf-org/scala-foundation-runtime-pure"),
                            "git@github.com:isomorf-org/scala-foundation-runtime-pure.git"))
developers := List(Developer("bdkent", "Brian Kent", "brian.kent@isomorf.io", url("https://github.com/bdkent")))

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

pomIncludeRepository := { _ => false }

publishMavenStyle := true

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value) {
    Opts.resolver.sonatypeSnapshots
  }
  else {
    Opts.resolver.sonatypeStaging
  }
)
  
EclipseKeys.withSource := true
EclipseKeys.useProjectId := true

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
