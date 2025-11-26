package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.CartRepository;

import java.nio.file.Path;

public class CartCsvRepositoryImpl implements CartRepository {

    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public CartCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
