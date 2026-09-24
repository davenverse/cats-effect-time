ThisBuild / tlBaseVersion := "0.3" // your current series x.y

ThisBuild / organization := "io.chrisdavenport"
ThisBuild / organizationName := "Christopher Davenport"
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("christopherdavenport", "Christopher Davenport")
)

ThisBuild / tlCiReleaseBranches := Seq("main")

val catsV = "2.9.0"
val catsEffectV = "3.4.9"

val munitCatsEffectV = "2.0.0-M3"

ThisBuild / crossScalaVersions := Seq("2.12.15","2.13.18", "3.3.8")
ThisBuild / scalaVersion := "3.3.8"
ThisBuild / versionScheme := Some("early-semver")

lazy val `cats-effect-time` = tlCrossRootProject
  .aggregate(core)

lazy val core = crossProject(JSPlatform, JVMPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "cats-effect-time",
    libraryDependencies ++= Seq(
      "org.typelevel"               %%% "cats-core"                  % catsV,
      "org.typelevel"               %%% "cats-effect"                % catsEffectV,
      "org.typelevel"               %%% "munit-cats-effect"          % munitCatsEffectV % Test,
      "org.typelevel"               %%% "cats-effect-laws"           % catsEffectV   % Test,
    ),
  ).jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  ).platformsSettings(JSPlatform, NativePlatform)(
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.5.0",
    ),
  ).nativeSettings(
    tlVersionIntroduced := List("2.12", "2.13", "3").map(_ -> "0.2.1").toMap
  )

lazy val site = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .settings(
    laikaTheme := tlSiteHelium.value.site
      .topNavigationBar(
        homeLink = laika.helium.config.IconLink.internal(laika.ast.Path.Root / "index.md", laika.helium.config.HeliumIcon.home)
      )
      .build
  )
  .dependsOn(core.jvm)
