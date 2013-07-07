package com.imti.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.imti.framework.common.StringUtils;

public class CodeUtil {

	public static String decode(String src){
		try {
			if(StringUtils.isBlank(src)){
				return "";
			}
			if(src.indexOf("%") != -1){
				src = URLDecoder.decode(src, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return src;
	}
}
