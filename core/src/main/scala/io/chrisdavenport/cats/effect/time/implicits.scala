package io.chrisdavenport.cats.effect.time

import cats._
import cats.effect.Clock
import java.time._

/**
 * This object contains the syntax enhancements to make more convenient access to
 * JavaTime as methods can be made to appear automatically on Clock rather than
 * needing an explicitly new algebra. 
 * 
 * Drawbacks are if you are lacking a Functor constraint it will be difficult to
 * see why these enhancements are not in place. Using it without
 * Functor basically means you can't move anywhere with this data, so this 
 * seems a likely constraint to be in place. Additionally, all Clock implementations
 * of which I know have a Functor of that type as well.
 **/
object implicits {
  implicit class ClockOps[F[_]: Functor](private val c: Clock[F]){
    private implicit val C = c
    private val J = JavaTime[F]

    /**
     * Get the current Instant with millisecond precision
     * from the epoch.
     **/
    def getInstant: F[Instant] = J.getInstant

    /**
     * Get the current LocalDate in the provided ZoneId
     **/
    def getLocalDate(zone: ZoneId): F[LocalDate] = J.getLocalDate(zone)
    /**
     * Get the current LocalDate in UTC Zone
     **/
    def getLocalDateUTC: F[LocalDate] = J.getLocalDateUTC

    /**
     * Get the current LocalDateTime in the provided ZoneId
     **/
    def getLocalDateTime(zone: ZoneId): F[LocalDateTime] = J.getLocalDateTime(zone)
    /**
     * Get the current LocalDateTime in UTC Zone
     **/
    def getLocalDateTimeUTC: F[LocalDateTime] = J.getLocalDateTimeUTC
  
    /**
     * Get the current LocalTime in the provided ZoneId
     **/
    def getLocalTime(zone: ZoneId): F[LocalTime] = J.getLocalTime(zone)
    /**
     * Get the current LocalTime in UTC Zone
     **/
    def getLocalTimeUTC: F[LocalTime] = J.getLocalTimeUTC

    /**
     * Get the current Year in the provided ZoneId
     **/
    def getYear(zone: ZoneId): F[Year] = J.getYear(zone)
    /**
     * Get the current Year in UTC Zone
     **/
    def getYearUTC: F[Year] = J.getYearUTC
  
    /**
     * Get the current YearMonth in the provided ZoneId
     **/
    def getYearMonth(zone: ZoneId): F[YearMonth] = J.getYearMonth(zone)
    /**
     * Get the current YearMonth in UTC Zone
     **/
    def getYearMonthUTC: F[YearMonth] = J.getYearMonthUTC
  
    /**
     * Get the current ZonedDateTime in the provided ZoneId
     **/
    def getZonedDateTime(zone: ZoneId): F[ZonedDateTime] = J.getZonedDateTime(zone)
    /**
     * Get the current ZonedDateTime in UTC Zone
     **/
    def getZonedDateTimeUTC: F[ZonedDateTime] = J.getZonedDateTimeUTC
  }
}