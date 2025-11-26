package com.ecommerce.exceptions;

public class ProductNotFoundInCartException extends Exception {
    public ProductNotFoundInCartException(String msg) {
        super(msg);
    }
}
