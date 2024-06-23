package com.btork.toolrental.exceptions;

public class InvalidRentalDayCountException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRentalDayCountException() {
		super();
	}
	
	public InvalidRentalDayCountException(String message) {
		super(message);
	}
	
    public InvalidRentalDayCountException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidRentalDayCountException(Throwable cause) {
        super(cause);
    }

}
