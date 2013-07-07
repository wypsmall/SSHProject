package com.imti.framework.component.sertificate.common;

import org.springframework.util.Assert;

public class SertificateAssert extends Assert {
	public static void isNotBlank(Object oject, String message){
		if(oject == null || "".equals(oject.toString())){
			throw new NullPointerException(message);
		}
	}
}
