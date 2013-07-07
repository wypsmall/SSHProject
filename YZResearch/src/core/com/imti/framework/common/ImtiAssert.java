package com.imti.framework.common;

import java.util.List;

import org.springframework.util.Assert;

public class ImtiAssert extends Assert {
	public static void isNotBlank(Object oject, String message){
		if(oject == null || "".equals(oject.toString())){
			throw new RuntimeException(message);
		}
	}
	public static void isListNotBlank(List list, String message){
		if(list == null && list.isEmpty()){
			throw new RuntimeException(message);
		}
	}
	public static void isArrayNotBlank(Object[] objArr, String message){
		if(objArr == null || objArr.length == 0){
			throw new RuntimeException(message);
		}
	}
	public static void warn(String message){
		throw new RuntimeException(message);
	}
}
