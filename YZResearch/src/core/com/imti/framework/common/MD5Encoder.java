package com.imti.framework.common;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

/**
 * @describe
 * @version:
 * @projectName netshop
 * @companyName EXPRESSTHOUGHT
 * @author KEN
 * @date Dec 18, 2007
 */
public class MD5Encoder {
private static Logger logger=Logger.getLogger(MD5Encoder.class);
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 *
	 *
	 *
	 * @param b
	 *
	 * @return 16
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		int d1 = 0;
		int d2 = 0;
		if (n < 0) {
			n = 256 + n;
			d1 = n / 16;
			d2 = n % 16;

		}
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5Encode(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception e) {
           logger.info("Exception:",e);
			logger.error(e.getMessage());
		}
		return resultString;
	}

	public static void main(String[] args) {
		System.out.println(md5Encode("a"));
	}
}