package com.neo.common.exception;


public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2477721701289210888L;
	
	public static void ThrowException(String errorMsg) {
		throw new RuntimeException(errorMsg);
	}
}
