package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.CartItemRepository;

import java.nio.file.Path;

public class CartItemCsvRepositoryImpl implements CartItemRepository {
    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public CartItemCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
