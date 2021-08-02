package io.chrisdavenport.cats.effect.time

import munit.CatsEffectSuite
import cats._
import cats.effect._

import cats.effect.laws.util.TestContext
import java.time._

class JavaTimeSpec extends CatsEffectSuite {

  // Additional Tests to Implement: 
  // def getLocalDateTime(zone: ZoneId): F[LocalDateTime]
  // def getLocalDateTimeUTC: F[LocalDateTime]

  // def getLocalTime(zone: ZoneId): F[LocalTime]
  // def getLocalTimeUTC: F[LocalTime]

  // def getYearMonth(zone: ZoneId): F[YearMonth]
  // def getYearMonthUTC: F[YearMonth]

  // def getZonedDateTime(zone: ZoneId): F[ZonedDateTime]
  // def getZonedDateTimeUTC: F[ZonedDateTime]

  test("getInstant the epoch in a test context") {
    implicit val ec: TestContext = TestContext()
    implicit val T: Timer[IO] = ec.timer[IO]
    val test = JavaTime.fromClock(T.clock, Functor[IO]).getInstant
    test.map(it =>  assertEquals(it, Instant.EPOCH))
  }

  test("getLocalDate the epoch in a test context") {
    implicit val ec: TestContext = TestContext()
    implicit val T: Timer[IO] = ec.timer[IO]
    val test = JavaTime.fromClock(T.clock, Functor[IO]).getLocalDate(ZoneOffset.UTC)
    test.map(it => assertEquals(it, LocalDate.ofEpochDay(0)))
  }

  test("getLocalDateUTC the epoch in a test context") {
    implicit val ec: TestContext = TestContext()
    implicit val T: Timer[IO] = ec.timer[IO]
    val test = JavaTime.fromClock(T.clock, Functor[IO]).getLocalDateUTC
    test.map(it => assertEquals(it, LocalDate.ofEpochDay(0)))
  }

  test("get the epoch year from test context"){
    implicit val ec: TestContext = TestContext()
    implicit val T: Timer[IO] = ec.timer[IO]
    val test = JavaTime.fromClock(T.clock, Functor[IO]).getYearUTC
    test.map(it => assertEquals(it, Year.of(1970)))
  }

}