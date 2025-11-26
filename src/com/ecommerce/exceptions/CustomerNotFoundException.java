package com.ecommerce.exceptions;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
