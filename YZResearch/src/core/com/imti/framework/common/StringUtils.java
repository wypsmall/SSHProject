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
 * �ַ���������
 *	
 * @author ����� �������ڣ�2009-4-27����04:06:40
 * @version tolerate 1.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	
	/**
	 * ���ַ���ת��
	 * 
	 * @param s	��ת���ַ���
	 * @param sourceCoding	ԭ����
	 * @param destCoding	ת������
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
	 * ����������ַ�����UTF-8�ַ���ת��ISO-8859-1�ַ���
	 * 
	 * @param s ��ת�����ַ���
	 * @return ת������ַ���
	 */
	public static String utf8ToIso88591Encoding(String s) {
		return transcoding(s, "UTF-8", "ISO-8859-1");
	}
	
	/**
	 * ��ISO-8859-1�ַ���ת��UTF-8�ַ���
	 * 
	 * @param s ��ת�����ַ���
	 * @return ת������ַ���
	 */
	public static String iso88591ToUtf8Encoding(String s) {
		return transcoding(s, "ISO-8859-1", "UTF-8");
	}
	
	/**
	 * ��ISO-8859-1�ַ���ת��GBK�ַ���
	 * 
	 * @param s ��ת�����ַ���
	 * @return ת������ַ���
	 */
	public static String iso88591ToGbkEncoding(String s) {
		return transcoding(s, "ISO-8859-1", "GBK");
	}
	
	/**
	 * ��GBK�ַ���ת��ISO-8859-1�ַ���
	 * 
	 * @param s ��ת�����ַ���
	 * @return ת������ַ���
	 */
	public static String gbkToIso88591Encoding(String s) {
		return transcoding(s, "GBK", "ISO-8859-1");
	}
	
	/**
	 * URL����
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
	 * URL����
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
	 * ����������ַ���ת��Ϊ������
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
	 * ����������ַ�������ת���ɳ���������
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
	 * ����������ַ���ת��Ϊ����
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
	 * ����������ַ�������ת������������
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
	 * ����������ַ���ת��Ϊ˫���ȸ�����
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
	 * ����������ַ�������ת����˫���ȸ���������
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
	 * ��������ַ�������yyyy-MM-dd HH:mm:ss.SSS��ʽת��Date���Ͷ���
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
	 * ��������ַ�������ָ����ʽת��Date���Ͷ���
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
