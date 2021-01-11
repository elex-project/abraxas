/*
 * Project Abraxas (a.k.a core-utils)
 * http://www.elex-project.com/
 * Copyright (c) 2017. Elex. All Rights Reserved.
 */

package com.elex_project.abraxas;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 시간 관련 유틸리티
 * Created by Elex on 2016-07-03.
 *
 * @author Elex
 * @since 1.8
 */
public final class Timez {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * {@value}
	 */
	public static final long TIMEUNIT_SEC = 1000;
	/**
	 * {@value}
	 */
	public static final long TIMEUNIT_MIN = 60 * TIMEUNIT_SEC;
	/**
	 * {@value}
	 */
	public static final long TIMEUNIT_HOUR = 60 * TIMEUNIT_MIN;
	/**
	 * {@value}
	 */
	public static final long TIMEUNIT_DAY = 24 * TIMEUNIT_HOUR;
	/**
	 * {@value}
	 */
	public static final long TIMEUNIT_WEEK = 7 * TIMEUNIT_DAY;

	//public static final ZoneId ZONE_ID_SEOUL = ZoneId.of("Asia/Seoul");
	//public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
	//		.ofPattern("yyyy-MM-dd'T'HH:mm:ssz");

	private Timez() {
	}

	/**
	 * 현재 시간을 밀리초로 반환
	 * Same as {@link #now()}
	 *
	 * @return {@link System#currentTimeMillis()}
	 */
	public static long currentTime() {
		return System.currentTimeMillis();
	}

	public static long currentNanoTime() {
		return System.nanoTime();
	}

	/**
	 * 현재 시간을 밀리초로 반환
	 *
	 * @return {@link System#currentTimeMillis()}
	 */
	public static long now() {
		return System.currentTimeMillis();
	}


	@NotNull
	public static String format(long datetime, final DateFormat dateFormat) {
		return dateFormat.format(new Date(datetime));
	}

	/**
	 * ISO Date/Time format
	 *
	 * @param datetime epoch 시간
	 * @return "yyyy-MM-dd'T'HH:mm:ss"
	 */
	public static String format(long datetime) {
		return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date(datetime));
	}

	public static long toEpoch(@NotNull final ZonedDateTime dateTime) {
		return dateTime.toInstant().toEpochMilli();
	}

	public static long toEpoch(@NotNull final OffsetDateTime dateTime) {
		return dateTime.toInstant().toEpochMilli();
	}

	public static long toEpoch(@NotNull final LocalDateTime dateTime, @NotNull final ZoneOffset zoneOffset) {
		return toEpoch(dateTime.atOffset(zoneOffset));
	}

	public static long toEpoch(@NotNull final LocalDateTime dateTime, @NotNull final ZoneId zoneId) {
		return toEpoch(dateTime.atZone(zoneId));
	}

	public static long toEpoch(@NotNull final Calendar calendar) {
		return calendar.getTimeInMillis();
	}

	public static long toEpoch(@NotNull final Date date) {
		return date.getTime();
	}

	@NotNull
	public static GregorianCalendar toCalendar(@NotNull final LocalDateTime dateTime, @NotNull final ZoneId zoneId) {
		return toCalendar(ZonedDateTime.of(dateTime, zoneId));
	}

	@NotNull
	public static GregorianCalendar toCalendar(@NotNull final ZonedDateTime dateTime) {
		return GregorianCalendar.from(dateTime);
	}

	@NotNull
	public static GregorianCalendar toCalendar(@NotNull final OffsetDateTime dateTime) {
		return toCalendar(dateTime.toZonedDateTime());
	}

	@NotNull
	public static Calendar toCalendar(@NotNull final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	@NotNull
	public static Calendar toCalendar(final long epoch) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(epoch);
		return calendar;
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final Calendar calendar, @NotNull final ZoneId zoneId) {
		return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final GregorianCalendar calendar) {
		return calendar.toZonedDateTime().toLocalDateTime();
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final Date date, @NotNull final ZoneId zoneId) {
		return LocalDateTime.ofInstant(date.toInstant(), zoneId);
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final ZonedDateTime date) {
		return date.toLocalDateTime();
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final ZonedDateTime date, @NotNull ZoneId zoneId) {
		return date.withZoneSameInstant(zoneId).toLocalDateTime();
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(@NotNull final OffsetDateTime date) {
		return date.toLocalDateTime();
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(final long epoch, @NotNull final ZoneId zoneId) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), zoneId);
	}

	@NotNull
	public static LocalDateTime toLocalDateTime(final long epoch) {
		return toLocalDateTime(epoch, ZoneId.systemDefault());
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(@NotNull final LocalDateTime dateTime, @NotNull final ZoneId zoneId) {
		return ZonedDateTime.of(dateTime, zoneId);
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(@NotNull final OffsetDateTime dateTime) {
		return dateTime.toZonedDateTime();
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(@NotNull final Calendar calendar, @NotNull final ZoneId zoneId) {
		return ZonedDateTime.ofInstant(calendar.toInstant(), zoneId);
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(@NotNull final GregorianCalendar calendar) {
		return calendar.toZonedDateTime();
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(@NotNull final Date date, @NotNull final ZoneId zoneId) {
		return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
	}

	@NotNull
	public static ZonedDateTime toZonedDateTime(final long epoch, @NotNull final ZoneId zoneId) {
		return toZonedDateTime(toLocalDateTime(epoch, zoneId), zoneId);
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final ZonedDateTime dateTime) {
		return dateTime.toOffsetDateTime();
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final LocalDateTime dateTime, @NotNull final ZoneOffset zoneOffset) {
		return dateTime.atOffset(zoneOffset);
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final LocalDateTime dateTime, @NotNull final ZoneId zoneId) {
		return dateTime.atZone(zoneId).toOffsetDateTime();
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final Calendar calendar, @NotNull final ZoneId zoneId) {
		return OffsetDateTime.ofInstant(calendar.toInstant(), zoneId);
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final GregorianCalendar calendar) {
		return calendar.toZonedDateTime().toOffsetDateTime();
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(@NotNull final Date date, @NotNull final ZoneId zoneId) {
		return OffsetDateTime.ofInstant(date.toInstant(), zoneId);
	}

	@NotNull
	public static OffsetDateTime toOffsetDateTime(final long epoch, @NotNull final ZoneId zoneId) {
		return toOffsetDateTime(toLocalDateTime(epoch, zoneId), zoneId);
	}

	@NotNull
	public static ZoneId toZoneId(@NotNull final TimeZone timeZone) {
		return timeZone.toZoneId();
	}

	@NotNull
	public static TimeZone toTimeZone(@NotNull final ZoneId zoneId) {
		return TimeZone.getTimeZone(zoneId);
	}

	@NotNull
	public static ZoneId toZoneId(@NotNull final ZoneOffset zoneOffset) {
		return zoneOffset.normalized();
	}

	@NotNull
	public static ZoneOffset toZoneOffset(@NotNull final ZoneId zoneId, @NotNull final Instant instant) {
		return zoneId.getRules().getOffset(instant);
	}

	@NotNull
	public static ZoneOffset toZoneOffset(final int hours) {
		return ZoneOffset.ofHours(hours);
	}

	@NotNull
	public static ZoneOffset toZoneOffset(final int hours, final int minutes) {
		return ZoneOffset.ofHoursMinutes(hours, minutes);
	}

	@NotNull
	public static Duration duration(@NotNull final Temporal t1, @NotNull final Temporal t2) {
		return Duration.between(t1, t2).abs();
	}

	@NotNull
	public static Duration duration(final long millis) {
		return Duration.ofMillis(millis);
	}

	@NotNull
	public static LocalDateTime parseToLocalDateTime(@NotNull final String date) throws DateTimeParseException {
		return LocalDateTime.parse(date);
	}

	@NotNull
	public static LocalDateTime parseToLocalDateTime(@NotNull final String date, @NotNull final DateTimeFormatter formatter) throws DateTimeParseException {
		return LocalDateTime.parse(date, formatter);
	}

	@NotNull
	public static LocalDate parseToLocalDate(@NotNull final String date) throws DateTimeParseException {
		return LocalDate.parse(date);
	}

	@NotNull
	public static LocalDate parseToLocalDate(@NotNull final String date, @NotNull final DateTimeFormatter formatter) throws DateTimeParseException {
		return LocalDate.parse(date, formatter);
	}

	@NotNull
	public static LocalTime parseToLocalTime(@NotNull final String date) throws DateTimeParseException {
		return LocalTime.parse(date);
	}

	@NotNull
	public static LocalTime parseToLocalTime(@NotNull final String date, @NotNull final DateTimeFormatter formatter) throws DateTimeParseException {
		return LocalTime.parse(date, formatter);
	}

	@NotNull
	public static ZonedDateTime parseToZonedDateTime(@NotNull final String date) throws DateTimeParseException {
		return ZonedDateTime.parse(date);
	}

	@NotNull
	public static ZonedDateTime parseToZonedDateTime(@NotNull final String date, @NotNull final DateTimeFormatter formatter) throws DateTimeParseException {
		return ZonedDateTime.parse(date, formatter);
	}

	@NotNull
	public static Instant parseToInstant(@NotNull final String date) throws DateTimeParseException {
		return Instant.parse(date);
	}
}
