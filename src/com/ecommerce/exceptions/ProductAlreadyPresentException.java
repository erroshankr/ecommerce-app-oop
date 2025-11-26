package com.ecommerce.exceptions;

public class ProductAlreadyPresentException extends Throwable {
    public ProductAlreadyPresentException(String msg) {
        super(msg);
    }
}
