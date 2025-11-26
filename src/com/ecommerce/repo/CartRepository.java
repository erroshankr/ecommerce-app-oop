package com.ecommerce.repo;

import com.ecommerce.exceptions.CartAlreadyPresentException;
import com.ecommerce.exceptions.CartNotFoundException;
import com.ecommerce.models.CartModel;

import java.io.IOException;
import java.util.List;

public interface CartRepository {

    void save(CartModel cart) throws IOException, CartAlreadyPresentException;
    CartModel findByCustomerId(String customerId) throws IOException, CartNotFoundException;
    void deleteByCustomerId(String customerId) throws IOException,CartNotFoundException;
    List<CartModel> findAll() throws IOException;

}
