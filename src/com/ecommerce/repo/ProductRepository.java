package com.ecommerce.repo;

import com.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.models.ProductModel;

import java.io.IOException;
import java.util.List;

public interface ProductRepository { //CRUD

    void addProduct(ProductModel p) throws IOException, ProductNotFoundException;
    ProductModel getProductBySku(String sku) throws IOException, ProductNotFoundException;
    ProductModel getProductById(String id) throws IOException, ProductNotFoundException;
    List<ProductModel> findAll() throws IOException;
    void removeProductBySku(String sku) throws IOException,ProductNotFoundException;
    void removeProductById(String id) throws IOException,ProductNotFoundException;
    void updateProduct(ProductModel p) throws IOException,ProductNotFoundException;

}

