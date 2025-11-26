package com.ecommerce.service.impl;

import com.ecommerce.models.CartModel;
import com.ecommerce.service.CartService;

public class CartServiceImpl implements CartService {

    @Override
    public void addToCart(String customerId, String sku, int qty) {

    }

    @Override
    public void removeFromCart(String customerId, String sku) {

    }

    @Override
    public CartModel getCart(String customerId) {
        return null;
    }
}
