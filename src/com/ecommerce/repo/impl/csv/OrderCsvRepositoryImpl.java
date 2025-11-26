package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.OrderRepository;

import java.nio.file.Path;

public class OrderCsvRepositoryImpl implements OrderRepository {
    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public OrderCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
