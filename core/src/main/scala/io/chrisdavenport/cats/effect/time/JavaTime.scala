package io.chrisdavenport.cats.effect.time

import cats._
import cats.implicits._
import cats.effect.Clock
import scala.concurrent.duration._
import java.time._

/**
 * This trait is a tagless representation of the ability to extract
 * the present time.
 * 
 * This algebra has millisecond precision from the Epoch as the
 * default Java Clock only offers millisecond precision. If you
 * need higher accuracy utilize the methods on Clock directly
 * which use the System tools with higher accuracy.
 * 
 * It is implicitly derived from Clock and Functor and these should
 * be readily available either through IOApp, IO.timer or in testing
 * you can have easy access to TestContext in order to manipulate
 * time for replicatable time for tests
 * 
 **/
@scala.annotation.implicitNotFound("""Cannot find implicit value for JavaTime[${F}].
Building this implicit value depends on having an implicit
Clock[${F}] and Functor[${F}] or some equivalent type.""")
trait JavaTime[F[_]]{
  /**
   * Get the current Instant with millisecond precision
   * from the epoch.
   **/
  def getInstant: F[Instant]

  /**
   * Get the current LocalDate in the provided ZoneId
   **/
  def getLocalDate(zone: ZoneId): F[LocalDate]
  /**
   * Get the current LocalDate in UTC Zone
   **/
  def getLocalDateUTC: F[LocalDate]

  /**
   * Get the current LocalDateTime in the provided ZoneId
   **/
  def getLocalDateTime(zone: ZoneId): F[LocalDateTime]
  /**
   * Get the current LocalDateTime in UTC Zone
   **/
  def getLocalDateTimeUTC: F[LocalDateTime]

  /**
   * Get the current LocalTime in the provided ZoneId
   **/
  def getLocalTime(zone: ZoneId): F[LocalTime]
  /**
   * Get the current LocalTime in UTC Zone
   **/
  def getLocalTimeUTC: F[LocalTime]

  /**
   * Get the current Year in the provided ZoneId
   **/
  def getYear(zone: ZoneId): F[Year]
  /**
   * Get the current Year in UTC Zone
   **/
  def getYearUTC: F[Year]

  /**
   * Get the current YearMonth in the provided ZoneId
   **/
  def getYearMonth(zone: ZoneId): F[YearMonth]
  /**
   * Get the current YearMonth in UTC Zone
   **/
  def getYearMonthUTC: F[YearMonth]

  /**
   * Get the current ZonedDateTime in the provided ZoneId
   **/
  def getZonedDateTime(zone: ZoneId): F[ZonedDateTime]
  /**
   * Get the current ZonedDateTime in UTC Zone
   **/
  def getZonedDateTimeUTC: F[ZonedDateTime]
}

/**
 * This companion object to the JavaTime Algebra
 * 
 * This contains the summoner for the algebra, along with 
 * the implicit instance which is derived from Clock and Functor.
 * 
 **/
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