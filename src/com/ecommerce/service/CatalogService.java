package com.ecommerce.service;

import com.ecommerce.models.ProductModel;

import java.util.List;

public interface CatalogService {

    boolean addProduct(ProductModel p);
    ProductModel findBySku(String sku);
    List<ProductModel> search(String q);
    List<ProductModel> findAll();
    ProductModel findById(String id);
    boolean updateProduct(ProductModel p);
    boolean removeProductById(String id);
    boolean removeProductBySku(String sku);

    boolean isStockPresent(String sku,  int quantity);
}
