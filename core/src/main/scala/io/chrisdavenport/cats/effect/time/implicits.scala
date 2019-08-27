package io.chrisdavenport.cats.effect.time

import cats._
import cats.effect.Clock
import java.time._

object implicits {
  implicit class ClockOps[F[_]: Functor](private val c: Clock[F]){
    private implicit val C = c
    private val J = JavaTime[F]

    def getInstant: F[Instant] = J.getInstant

    def getLocalDate(zone: ZoneId): F[LocalDate] = J.getLocalDate(zone)
    def getLocalDateUTC: F[LocalDate] = J.getLocalDateUTC

    def getLocalDateTime(zone: ZoneId): F[LocalDateTime] = J.getLocalDateTime(zone)
    def getLocalDateTimeUTC: F[LocalDateTime] = J.getLocalDateTimeUTC
  
    def getLocalTime(zone: ZoneId): F[LocalTime] = J.getLocalTime(zone)
    def getLocalTimeUTC: F[LocalTime] = J.getLocalTimeUTC

    def getYear(zone: ZoneId): F[Year] = J.getYear(zone)
    def getYearUTC: F[Year] = J.getYearUTC
  
    def getYearMonth(zone: ZoneId): F[YearMonth] = J.getYearMonth(zone)
    def getYearMonthUTC: F[YearMonth] = J.getYearMonthUTC
  
    def getZonedDateTime(zone: ZoneId): F[ZonedDateTime] = J.getZonedDateTime(zone)
    def getZonedDateTimeUTC: F[ZonedDateTime] = J.getZonedDateTimeUTC
  }
}