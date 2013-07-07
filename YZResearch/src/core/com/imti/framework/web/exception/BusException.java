package com.imti.framework.web.exception;

public class BusException extends RuntimeException {
	public BusException(String message) {
	    super(message);
	  }

	  public BusException(Throwable cause) {
	    super(cause);
	  }

	  public BusException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
