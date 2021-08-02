package io.chrisdavenport.cats.effect.time

import munit.CatsEffectSuite
import cats.effect._
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

  test("getInstant") {//in ticked { implicit ticker => 

    val test = JavaTime[IO].getInstant
    test.map{it =>  
      val x = Instant.now.toEpochMilli - it.toEpochMilli
      assert(x < 1000)
    }
  }

  test("getLocalDate") {
    val test = JavaTime[IO].getLocalDate(ZoneOffset.UTC)
    
    test.map{it => 
      val today = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toLocalDate
      assertEquals(it, today)
    }
  }

  test("get the epoch year"){
    val test = JavaTime[IO].getYearUTC
    test.map{it => 
      val year = Year.of(LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).toLocalDate.getYear())
      assertEquals(it, year)
    }
  }

}