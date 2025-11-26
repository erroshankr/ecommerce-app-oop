package com.ecommerce.service;

import com.ecommerce.models.ProductModel;

import java.util.List;

public interface CatalogService {

    void addProduct(ProductModel p);
    ProductModel findBySku(String sku);
    List<ProductModel> search(String q);
    List<ProductModel> findAll();
}
