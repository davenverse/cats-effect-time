package io.chrisdavenport.cats.effect.time

import cats._
import cats.implicits._
import cats.effect.Clock
import scala.concurrent.duration._
import java.time._

trait JavaTime[F[_]]{
  def getInstant: F[Instant]

  def getLocalDate(zone: ZoneId): F[LocalDate]
  def getLocalDateUTC: F[LocalDate]

  def getLocalDateTime(zone: ZoneId): F[LocalDateTime]
  def getLocalDateTimeUTC: F[LocalDateTime]

  def getLocalTime(zone: ZoneId): F[LocalTime]
  def getLocalTimeUTC: F[LocalTime]

  def getYear(zone: ZoneId): F[Year]
  def getYearUTC: F[Year]

  def getYearMonth(zone: ZoneId): F[YearMonth]
  def getYearMonthUTC: F[YearMonth]

  def getZonedDateTime(zone: ZoneId): F[ZonedDateTime]
  def getZonedDateTimeUTC: F[ZonedDateTime]
}

object JavaTime {
  
  def apply[F[_]](implicit ev: JavaTime[F]): JavaTime[F] = ev

  implicit def fromClock[F[_]](implicit C: Clock[F], F: Functor[F]): JavaTime[F] =
    new ClockJavaTime[F](C)(F)

  private class ClockJavaTime[F[_]: Functor](private val c: Clock[F]) extends JavaTime[F]{
    def getInstant: F[Instant] = 
      c.realTime(MILLISECONDS).map(Instant.ofEpochMilli(_))
    
    def getLocalDate(zone: ZoneId): F[LocalDate] =  
      getLocalDateTime(zone).map(_.toLocalDate)
    def getLocalDateUTC: F[LocalDate] = 
      getLocalDate(ZoneOffset.UTC)
    def getLocalDateTime(zone: ZoneId): F[LocalDateTime] = 
      getInstant.map(LocalDateTime.ofInstant(_, zone))
    def getLocalDateTimeUTC: F[LocalDateTime] =
      getLocalDateTime(ZoneOffset.UTC)
    def getLocalTime(zone: ZoneId) = 
      getLocalDateTime(zone).map(_.toLocalTime)
    def getLocalTimeUTC: F[LocalTime] =
      getLocalTime(ZoneOffset.UTC)
    
    def getYear(zone: ZoneId): F[Year] = 
      getLocalDate(zone).map(d => Year.of(d.getYear()))
    def getYearUTC: F[Year] = getYear(ZoneOffset.UTC)

    def getYearMonth(zone: ZoneId): F[YearMonth] = 
      getLocalDate(zone).map(d => YearMonth.of(d.getYear, d.getMonth))
    def getYearMonthUTC: F[YearMonth] =
      getYearMonth(ZoneOffset.UTC)
    
    def getZonedDateTime(zone: ZoneId): F[ZonedDateTime] = 
      getInstant.map(ZonedDateTime.ofInstant(_, zone))
    def getZonedDateTimeUTC: F[ZonedDateTime] =
      getZonedDateTime(ZoneOffset.UTC)
  }
}