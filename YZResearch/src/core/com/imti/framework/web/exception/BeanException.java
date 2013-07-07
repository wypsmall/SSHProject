package com.imti.framework.web.exception;

public class BeanException extends RuntimeException {
	public BeanException(String message) {
		super(message);
	}

	public BeanException(Throwable cause) {
		super(cause);
	}
	
	public BeanException(String message, Throwable cause) {
		super(message, cause);
	}
}
