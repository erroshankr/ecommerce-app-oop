package com.ecommerce.exceptions;

public class CartAlreadyPresentException extends Throwable {
    public CartAlreadyPresentException(String msg) {
        super(msg);
    }
}
