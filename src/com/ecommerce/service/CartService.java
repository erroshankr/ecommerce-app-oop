package com.ecommerce.service;

import com.ecommerce.models.CartModel;

public interface CartService {

    void addToCart(String customerId, String sku, int qty);
    void removeFromCart(String customerId, String sku);
    CartModel getCart(String customerId);

}
