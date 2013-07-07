package com.imti.framework.web.exception;


public class ConException extends RuntimeException {

	public ConException(String message) {
		super(message);
	}

	public ConException(Throwable cause) {
		super(cause);
	}

	public ConException(String message, Throwable cause) {
		super(message, cause);
	}

}
