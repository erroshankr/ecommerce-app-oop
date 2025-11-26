package com.ecommerce.exceptions;

public class CartItemAlreadyPresentException extends Exception {
    public CartItemAlreadyPresentException(String message) {
        super(message);
    }
}
