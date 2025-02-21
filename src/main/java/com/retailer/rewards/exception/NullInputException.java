package com.retailer.rewards.exception;

/**
 * Exception thrown when an input value is null and not allowed.
 */
public class NullInputException extends RuntimeException {

	 public NullInputException(String message) {
	        super(message);
	    }
}
