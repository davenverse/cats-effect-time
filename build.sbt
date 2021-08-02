import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}


ThisBuild / crossScalaVersions := Seq("2.12.14", "2.13.6", "3.0.0")

val catsV = "2.6.1"
val catsEffectV = "2.5.2"

val specs2V = "4.12.3"

lazy val `cats-effect-time` = project.in(file("."))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .aggregate(core.jvm, core.js)

lazy val core = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "cats-effect-time",
    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "cats-effect"                % catsEffectV,
      "org.typelevel" %%% "munit-cats-effect-2" % "1.0.5" % Test,
      "org.typelevel"               %%% "cats-effect-laws"           % catsEffectV   % Test,  
    )
    mimaVersionCheckExcludedVersions := {
      if (!isDotty.value) Set()
      else Set(
        "0.1.0",
        "0.1.1",
        "0.1.2"
      )
    }

  ).jsSettings(
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.3.0",
    ),
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )

lazy val site = project.in(file("site"))
  .disablePlugins(MimaPlugin)
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(DavenverseMicrositePlugin)
  .dependsOn(core.jvm)
  .settings(
    micrositeDescription := "Java Time from Cats-Effect",
  )

