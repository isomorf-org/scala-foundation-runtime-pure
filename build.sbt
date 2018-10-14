// shadow sbt-scalajs' crossProject and CrossType from Scala.js 0.6.x
import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

import ReleaseTransformations._

import com.typesafe.sbt.pgp.PgpKeys.publishSigned

enablePlugins(ScalaJSPlugin)

lazy val root = crossProject(JSPlatform, JVMPlatform)
    .withoutSuffixFor(JVMPlatform)
    .crossType(CrossType.Pure)
    .in(file("."))
    .settings(
	  // skip in publish := false,
      publishArtifact := true,
      publishTo := Some(if (isSnapshot.value) { Opts.resolver.sonatypeSnapshots } else { Opts.resolver.sonatypeStaging }),
    )
    .settings(
      // HACK: these settings do no seem to respond to being in ThisBuild like you would want:
      name := Common.name,
      releasePublishArtifactsAction := PgpKeys.publishSigned.value,
      releaseProcess := Seq[ReleaseStep](
    		checkSnapshotDependencies,
    		inquireVersions,
    		runClean,
    		runTest,
    		setReleaseVersion,
    		commitReleaseVersion,
    		tagRelease,
    		//publishArtifacts,
    		releaseStepCommandAndRemaining("+publishArtifacts"),
    		releaseStepCommandAndRemaining("makeDocs"),
    		setNextVersion,
    		commitNextVersion,
    		//releaseStepCommand(s"sonatypeReleaseAll ${Common.organization}"),
    		pushChanges
    	)
    )
    .jvmSettings(
      EclipseKeys.withSource := true,
      EclipseKeys.eclipseOutput := Some("./etarget")
    )
    .enablePlugins(SbtOsgi)
    .jvmSettings(osgiSettings)
    .jvmSettings(
      (unmanagedResourceDirectories in Compile) += baseDirectory.value / "../src/main/resources",
      OsgiKeys.exportPackage := Common.OSGi.exportPackage,
      OsgiKeys.importPackage := Common.OSGi.importPackage
    )
    .jsSettings(
      EclipseKeys.skipProject := true
    )

lazy val rootJVM = root.jvm

lazy val rootJS = root.js
