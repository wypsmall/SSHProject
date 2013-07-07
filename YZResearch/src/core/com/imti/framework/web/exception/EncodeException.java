package com.imti.framework.web.exception;


public class EncodeException extends RuntimeException {
	 public EncodeException(String message) {
	    super(message);
	  }

	  public EncodeException(Throwable cause) {
	    super(cause);
	  }

	  public EncodeException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
