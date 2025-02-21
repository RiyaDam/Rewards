package com.retailer.rewards.exception;

/**
 * Exception thrown when a transaction is not found in the system.
 */
public class TransactionNotFoundException extends RuntimeException {
	 public TransactionNotFoundException(String message) {
	        super(message);
	    }
	 
}