/**
 * 
 */
package com.imti.framework.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 *	
 * @author 李昊龙 新增日期：2009-4-27下午04:06:40
 * @version tolerate 1.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	/**
	 * 将字符串转码
	 * 
	 * @param s	待转码字符串
	 * @param sourceCoding	原编码
	 * @param destCoding	转换编码
	 * @return {@link String}
	 */
	public static String transcoding(String s, String sourceCoding, String destCoding) {
		try {
			return new String(s.getBytes(sourceCoding), destCoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * 将所传入的字符串从UTF-8字符集转至ISO-8859-1字符集
	 * 
	 * @param s 待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String utf8ToIso88591Encoding(String s) {
		return transcoding(s, "UTF-8", "ISO-8859-1");
	}
	
	/**
	 * 从ISO-8859-1字符集转至UTF-8字符集
	 * 
	 * @param s 待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String iso88591ToUtf8Encoding(String s) {
		return transcoding(s, "ISO-8859-1", "UTF-8");
	}
	
	/**
	 * 从ISO-8859-1字符集转至GBK字符集
	 * 
	 * @param s 待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String iso88591ToGbkEncoding(String s) {
		return transcoding(s, "ISO-8859-1", "GBK");
	}
	
	/**
	 * 从GBK字符集转至ISO-8859-1字符集
	 * 
	 * @param s 待转换的字符串
	 * @return 转换后的字符串
	 */
	public static String gbkToIso88591Encoding(String s) {
		return transcoding(s, "GBK", "ISO-8859-1");
	}
	
	/**
	 * URL编码
	 * 
	 * @param s
	 * @param encode
	 * @return
	 * @see URLEncoder.encode
	 */
	public static String urlEncode(String s, String encode) {
		try {
			return URLEncoder.encode(s, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * URL解码
	 * 
	 * @param s
	 * @param encode
	 * @return
	 * @see URLDecoder.decode
	 */
	public static String urlDecode(String s, String encode) {
		try {
			return URLDecoder.decode(s, encode);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将所传入的字符串转换为长整型
	 * 
	 * @param s
	 * @return Long
	 */
	public static Long parseLong(String s) {
		if (!isBlank(s)) {
			return Long.valueOf(s.trim());
		}
		return null;
	}
	
	/**
	 * 将所传入的字符串数组转换成长整型数组
	 * 
	 * @param s
	 * @return Long[]
	 */
	public static Long[] parseLong(String[] s) {
		Long[] arrayLong = new Long[s.length];
		for (int i = 0; i < s.length; i++) {
			arrayLong[i] = parseLong(s[i]);
		}
		
		return arrayLong;
	}
	
	/**
	 * 将所传入的字符串转换为整型
	 * 
	 * @param s
	 * @return Integer
	 */
	public static Integer parseInteger(String s) {
		if (!isBlank(s)) {
			return Integer.valueOf(s.trim());
		}
		return null;
	}
	
	/**
	 * 将所传入的字符串数组转换成整型数组
	 * 
	 * @param s
	 * @return Integer[]
	 */
	public static Integer[] parseInteger(String[] s) {
		Integer[] arrayInteger = new Integer[s.length];
		for (int i = 0; i < s.length; i++) {
			arrayInteger[i] = parseInteger(s[i]);
		}
		
		return arrayInteger;
	}
	
	/**
	 * 将所传入的字符串转换为双精度浮点型
	 * 
	 * @param s
	 * @return Double
	 */
	public static Double parseDouble(String s) {
		if (!isBlank(s)) {
			return Double.valueOf(s.trim());
		}
		return null;
	}
	
	/**
	 * 将所传入的字符串数组转换成双精度浮点型数组
	 * 
	 * @param s
	 * @return Double[]
	 */
	public static Double[] parseDouble(String[] s) {
		Double[] arrayDouble = new Double[s.length];
		for (int i = 0; i < s.length; i++) {
			arrayDouble[i] = parseDouble(s[i]);
		}
		
		return arrayDouble;
	}
	
	/**
	 * 将传入的字符串根据yyyy-MM-dd HH:mm:ss.SSS格式转成Date类型对象
	 * 
	 * @param s
	 * @return Date
	 */
	public static Date parseDate(String s) {
		try {
			if (!isBlank(s)) {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
			}
			return null;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将传入的字符串根据指定格式转成Date类型对象
	 * 
	 * @param s
	 * @return Date
	 */
	public static Date parseDate(String s, String format) {
		try {
			if (!isBlank(s)) {
				return new SimpleDateFormat(format).parse(s);
			}
			return null;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
