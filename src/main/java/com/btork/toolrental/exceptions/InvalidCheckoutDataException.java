package com.btork.toolrental.exceptions;

public class InvalidCheckoutDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCheckoutDataException() {
		super();
	}
	
	public InvalidCheckoutDataException(String message) {
		super(message);
	}
	
    public InvalidCheckoutDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidCheckoutDataException(Throwable cause) {
        super(cause);
    }

}
