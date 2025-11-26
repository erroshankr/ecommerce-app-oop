package com.ecommerce.repo.impl.csv;

import com.ecommerce.repo.InventoryRecordRepository;

import java.nio.file.Path;

public class InventoryRecordCsvRepositoryImpl implements InventoryRecordRepository {
    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public InventoryRecordCsvRepositoryImpl(Path csvpath) {
        this.csvpath = csvpath;
    }
}
