package com.ecommerce.exceptions;

public class CustomerAlreadyExistException extends Throwable {
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
