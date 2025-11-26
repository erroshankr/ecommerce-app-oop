package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.OrderRepository;

import java.nio.file.Path;

public class OrderCsvRepositoryImpl implements OrderRepository {
    private final Path csvpath;
    private final String HEADER = "orderId,customerId,subtotal,taxes,shipping,total,status";

    public OrderCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
