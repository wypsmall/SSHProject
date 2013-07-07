package com.imti.framework.web.exception;

public class FileException extends RuntimeException {
	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}
}
