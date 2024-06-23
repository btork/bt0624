package com.btork.toolrental.exceptions;

public class InvalidDiscountPercentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidDiscountPercentException() {
		super();
	}
	
	public InvalidDiscountPercentException(String message) {
		super(message);
	}
	
    public InvalidDiscountPercentException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidDiscountPercentException(Throwable cause) {
        super(cause);
    }

}
