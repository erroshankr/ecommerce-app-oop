package com.ecommerce.repo;

import com.ecommerce.exceptions.CartItemAlreadyPresentException;
import com.ecommerce.exceptions.CartItemNotFoundException;
import com.ecommerce.exceptions.CartNotFoundException;
import com.ecommerce.exceptions.ProductNotFoundInCartException;
import com.ecommerce.models.CartItemModel;

import java.io.IOException;
import java.util.List;

public interface CartItemRepository {

    void save(CartItemModel item) throws IOException, CartItemAlreadyPresentException;
    CartItemModel findById(String id) throws IOException, CartNotFoundException;
    List<CartItemModel> findAllByCartId(String cartId) throws IOException, CartNotFoundException;
    CartItemModel findByCartIdAndSku(String cartId, String sku) throws IOException, CartNotFoundException, ProductNotFoundInCartException;
    void updateQuantityBySku(String sku, int quantity) throws IOException, ProductNotFoundInCartException;
    void deleteBySku(String sku) throws IOException, ProductNotFoundInCartException;
    void deleteById(String id) throws IOException, CartItemNotFoundException;
    List<CartItemModel> findAll() throws IOException;
}
