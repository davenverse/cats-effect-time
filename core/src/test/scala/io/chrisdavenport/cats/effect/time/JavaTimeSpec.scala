package io.chrisdavenport.cats.effect.time

import org.specs2._
import cats.effect._

import cats.effect.laws.util.TestContext
import java.time._

object JavaTimeSpec extends mutable.Specification {

  // Additional Tests to Implement: 
  // def getLocalDateTime(zone: ZoneId): F[LocalDateTime]
  // def getLocalDateTimeUTC: F[LocalDateTime]

  // def getLocalTime(zone: ZoneId): F[LocalTime]
  // def getLocalTimeUTC: F[LocalTime]

  // def getYearMonth(zone: ZoneId): F[YearMonth]
  // def getYearMonthUTC: F[YearMonth]

  // def getZonedDateTime(zone: ZoneId): F[ZonedDateTime]
  // def getZonedDateTimeUTC: F[ZonedDateTime]

  "JavaTime" should {
    "getInstant the epoch in a test context" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]
      val test = JavaTime[IO].getInstant
      test.unsafeRunSync() must_=== Instant.EPOCH
    }

    "getLocalDate the epoch in a test context" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]
      val test = JavaTime[IO].getLocalDate(ZoneOffset.UTC)
      test.unsafeRunSync() must_=== LocalDate.ofEpochDay(0)
    }

    "getLocalDateUTC the epoch in a test context" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]
      val test = JavaTime[IO].getLocalDateUTC
      test.unsafeRunSync() must_=== LocalDate.ofEpochDay(0)
    }

    "get the epoch year from test context" in {
      implicit val ec = TestContext()
      implicit val T = ec.timer[IO]
      val test = JavaTime[IO].getYearUTC
      test.unsafeRunSync() must_=== Year.of(1970)
    }
  }

}