package com.ecommerce.service;

public interface InventoryService {

    void reserve(String sku, int qty);
    void release(String sku, int qty);
    int available(String sku);
}
