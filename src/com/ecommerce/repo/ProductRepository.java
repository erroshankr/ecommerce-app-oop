package com.ecommerce.repo;

import com.ecommerce.exceptions.ProductAlreadyPresentException;
import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.ProductModel;

import java.io.IOException;
import java.util.List;

// One Repo == One model
// talks to models present in data-store
//CREATE(object),READ(field), UPDATE(object), DELETE(field)
public interface ProductRepository { //CRUD

    void addProduct(ProductModel p) throws IOException, ProductAlreadyPresentException;
    ProductModel getProductById(String id) throws IOException, ProductNotFoundException;
    void updateProduct(ProductModel p) throws IOException,ProductNotFoundException;
    void removeProductById(String id) throws IOException,ProductNotFoundException;

    ProductModel getProductBySku(String sku) throws IOException, ProductNotFoundException;
    void removeProductBySku(String sku) throws IOException,ProductNotFoundException;

    List<ProductModel> findAll() throws IOException;



}

