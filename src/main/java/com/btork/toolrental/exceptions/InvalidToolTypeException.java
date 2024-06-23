package com.btork.toolrental.exceptions;

public class InvalidToolTypeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidToolTypeException() {
		super();
	}
	
	public InvalidToolTypeException(String message) {
		super(message);
	}
	
    public InvalidToolTypeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidToolTypeException(Throwable cause) {
        super(cause);
    }

}
