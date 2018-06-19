
// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}


import ReleaseTransformations._
    
lazy val foundationRuntimePure = crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(commonSettings: _*)
    .settings(publishingSettings: _*)
    .jvmSettings(eclipseSettings: _*)
    .settings(
      libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
    
lazy val foundationRuntimePureJVM = foundationRuntimePure.jvm
lazy val foundationRuntimePureJS = foundationRuntimePure.js
  
  
val commonSettings = Seq(
  organization := "org.isomorf",
  name         := "foundation-runtime-pure",
  scalaVersion := "2.12.3",
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8", "-Xlint:_", "-Ywarn-unused-import"),
  unmanagedSourceDirectories in Compile := (scalaSource in Compile).value :: Nil,
  unmanagedSourceDirectories in Test := (scalaSource in Test).value :: Nil
)

val noPublishingSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publishTo := Some(Resolver.file("Unused transient repository", file("target/unusedrepo")))
)

val publishingSettings = Seq(
//useGpg := true,
  homepage   := Some(url("https://github.com/isomorf-org/scala-foundation-runtime-pure")),
  scmInfo    := Some(ScmInfo(url("https://github.com/isomorf-org/scala-foundation-runtime-pure"),
                              "git@github.com:isomorf-org/scala-foundation-runtime-pure.git")),
  developers := List(Developer("bdkent", "Brian Kent", "brian.kent@isomorf.io", url("https://github.com/bdkent"))),

  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),

  pomIncludeRepository := { _ => false },

  publishMavenStyle := true,
  
  // Add sonatype repository settings
  publishTo := Some(
    if (isSnapshot.value) {
      Opts.resolver.sonatypeSnapshots
    }
    else {
      Opts.resolver.sonatypeStaging
    }
  ),
  
  
  releaseCrossBuild := true,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    //releaseStepCommand("publishSigned"),
    releaseStepCommandAndRemaining("+publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

val eclipseSettings = Seq(
  EclipseKeys.withSource := true
  //,
  //EclipseKeys.useProjectId := true
)

publishTo := Some(Resolver.file("Unused transient repository", file("target/unusedrepo")))
