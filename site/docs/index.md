---
layout: home

---

# cats-effect-time - Java Time from Cats-Effect [![Build Status](https://travis-ci.com/ChristopherDavenport/cats-effect-time.svg?branch=master)](https://travis-ci.com/ChristopherDavenport/cats-effect-time) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/cats-effect-time_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chrisdavenport/cats-effect-time_2.12)

## Quick Start

To use cats-effect-time in an existing SBT project with Scala 2.11 or a later version, add the following dependencies to your
`build.sbt` depending on your needs:

```scala
libraryDependencies ++= Seq(
  "io.chrisdavenport" %% "cats-effect-time" % "<version>"
)
```


## Example


```scala mdoc
import cats.effect._
import scala.concurrent.ExecutionContext.global
import io.chrisdavenport.cats.effect.time.JavaTime

implicit val T: Timer[IO] = IO.timer(global)

// Works with a valid Functor and Clock in scope
val currentTimeOp = JavaTime[IO].getInstant

currentTimeOp.unsafeRunSync()

// Or you can use it implicitly
import io.chrisdavenport.cats.effect.time.implicits._

val currentTime = Clock[IO].getZonedDateTimeUTC
currentTime.unsafeRunSync()
```