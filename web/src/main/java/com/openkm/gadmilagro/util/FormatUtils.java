/**
 * OpenKM, Open Document Management System (http://www.openkm.com)
 * Copyright (c) Paco Avila & Josep Llort
 * <p>
 * No bytes were intentionally harmed during the development of this application.
 * <p>
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.gadmilagro.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by pavila on 3/11/15.
 */
public class FormatUtils {
	private static final Locale locale = new Locale("es", "ES");
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	private static final String[] UNITS = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};

	/**
	 * Format date
	 */
	public static String formatDate(Date date) {
		return formatDate(date, DATE_FORMAT);
	}

	/**
	 * Format date
	 */
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * Format date
	 */
	public static String formatDateTime(Calendar date) {
		return new SimpleDateFormat(DATETIME_FORMAT).format(date.getTime());
	}

	/**
	 * Format calendar
	 */
	public static String formatCalendar(Calendar date) {
		return formatCalendar(date, DATE_FORMAT);
	}

	/**
	 * Format calendar
	 */
	public static String formatCalendar(Calendar date, String pattern) {
		return new SimpleDateFormat(pattern).format(date.getTime());
	}

	/**
	 * Parse date
	 */
	public static Date parseDate(String str) throws ParseException {
		return parseDate(str, DATE_FORMAT);
	}

	/**
	 * Parse date
	 */
	public static Date parseDate(String str, String pattern) throws ParseException {
		java.util.Date date = new SimpleDateFormat(pattern).parse(str);
		return new Date(date.getTime());
	}

	/**
	 * Format number
	 */
	public static String formatNumber(BigDecimal num) {
		NumberFormat numFmt = NumberFormat.getInstance(locale);
		numFmt.setMinimumFractionDigits(2);
		return numFmt.format(num);
	}

	/**
	 * Format currency
	 */
	public static String formatCurrency(BigDecimal num) {
		return formatNumber(num) + " €";
	}

	/**
	 * Parse number
	 */
	public static BigDecimal parseNumber(String str) throws ParseException {
		DecimalFormat decFmt = (DecimalFormat) NumberFormat.getInstance(locale);
		decFmt.setParseBigDecimal(true);
		return (BigDecimal) decFmt.parse(str);
	}

	/**
	 * Parse number
	 */
	public static BigDecimal parseNumber(String str, String pattern) throws ParseException {
		DecimalFormat decFmt = new DecimalFormat(pattern);
		decFmt.setParseBigDecimal(true);
		return (BigDecimal) decFmt.parse(str);
	}

	/**
	 * Format the document size for human readers
	 */
	public static String formatSize(long bytes) {
		for (int i = 6; i > 0; i--) {
			double step = Math.pow(1024, i);
			if (bytes > step)
				return String.format(Locale.ROOT, "%3.1f %s", bytes / step, UNITS[i]);
		}

		return bytes + " " + UNITS[0];
	}

	public static String parsePropSelectOptionValue(String propertyValue) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<String> parsedValue = mapper.readValue(propertyValue, List.class);

			if (parsedValue != null && !parsedValue.isEmpty()) {
				return parsedValue.get(0);
			} else {
				return propertyValue;
			}
		} catch (Exception e) {
			return propertyValue;
		}
	}

	public static String toJson(String value) {
		return new Gson().toJson(Collections.singletonList(value));
	}
}
