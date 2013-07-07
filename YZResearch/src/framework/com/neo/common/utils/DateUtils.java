package com.neo.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public enum DateType {
		YEAR, MONTH, DAY, HH, MI, SS, YYYY_MM_DD, YYYYMMDD
	}

	public static String FORMAT_DATE_TIME = "yyyy-MM-dd";
	public static String FORMAT_DATE_TIME_LONG = "yyyy-MM-dd HH:mm:ss";
	public final static String SHORT_DATE_FORMAT = "yyyyMMdd";

	public static String convertDateTOString(Date date) {
		if(date == null)
			return "";
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
		return dateFormat.format(date);
	}

	public static String convertDateTOString(Date date,String dateFormat) {
		if(date == null || dateFormat==null)
			return "";
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}

	public static String convertDateTOLongString(Date date) {
		if(date == null)
			return "";
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_LONG);
		return dateFormat.format(date);
	}

	public static Timestamp convertStringTOTimestamp(String string) {
		if(string == null || string.length() == 0)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp convertLongStringTOTimestamp(String string) {
		if(string == null || string.length() == 0)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_LONG);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return new Timestamp(date.getTime());
	}

	public static Date convertStringTODate(String string) {
		if(string == null || string.length() == 0)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Date convertLongStringTODate(String string) {
		if(string == null || string.length() == 0)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_TIME_LONG);
		Date date = null;
		try {
			date = dateFormat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date addDate(Date date, DateType dateType, int num) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date);
		if (DateType.YEAR.equals(dateType)) {
			c1.add(Calendar.YEAR, num);
			return c1.getTime();
		}
		if (DateType.MONTH.equals(dateType)) {
			c1.add(Calendar.MONTH, num);
			return c1.getTime();
		}
		if (DateType.DAY.equals(dateType)) {
			c1.add(Calendar.DAY_OF_MONTH, num);
			return c1.getTime();
		}
		if (DateType.HH.equals(dateType)) {
			c1.add(Calendar.HOUR_OF_DAY, num);
			return c1.getTime();
		}
		if (DateType.MI.equals(dateType)) {
			c1.add(Calendar.MINUTE, num);
			return c1.getTime();
		}
		if (DateType.SS.equals(dateType)) {
			c1.add(Calendar.SECOND, num);
			return c1.getTime();
		}
		return date;
	}


}
