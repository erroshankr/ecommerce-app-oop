package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.CartRepository;

import java.nio.file.Path;

public class CartCsvRepositoryImpl implements CartRepository {

    private final Path csvpath;
    private final String HEADER = "cartId,customerId,sku,quantity,subtotal,tax";

    public CartCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
