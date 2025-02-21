package com.retailer.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage application-wide exceptions.
 * It provides centralized exception handling across all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * Handles exceptions when a required input is null.
     *
     * @param ex the exception thrown when a null input is encountered.
     * @return a response entity with a BAD_REQUEST status and the exception message.
     */
	@ExceptionHandler(NullInputException.class)
    public ResponseEntity<String> handleNullInputException(NullInputException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	 /**
     * Handles exceptions when a transaction is not found.
     *
     * @param ex the exception thrown when a transaction is not found.
     * @return a response entity with a NOT_FOUND status and the exception message.
     */
	@ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

	 /**
     * Handles exceptions when a customer is not found.
     *
     * @param ex the exception thrown when a customer is not found.
     * @return a response entity with a NOT_FOUND status and the exception message.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles generic exceptions that are not explicitly handled by other methods.
     *
     * @param ex the exception thrown for unexpected errors.
     * @return a response entity with an INTERNAL_SERVER_ERROR status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
