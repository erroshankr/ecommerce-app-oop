package com.ecommerce.exceptions;

public class CartItemNotFoundException extends Exception {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
