package com.btork.toolrental.exceptions;

public class InvalidToolCodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidToolCodeException() {
		super();
	}
	
	public InvalidToolCodeException(String message) {
		super(message);
	}
	
    public InvalidToolCodeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidToolCodeException(Throwable cause) {
        super(cause);
    }

}
