package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.OrderItemRepository;

import java.nio.file.Path;

public class OrderItemCsvRepositoryImpl implements OrderItemRepository {
    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public OrderItemCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
