package com.ecommerce.repo.impl.csv;

import com.ecommerce.models.CustomerModel;
import com.ecommerce.repo.CustomerRepository;

import java.nio.file.Path;

public class CustomerCsvRepoImpl implements CustomerRepository {

    private final Path csvpath;
    private final String HEADER = "id,name,phone,email,address";

    public CustomerCsvRepoImpl(Path csvpath) {
        this.csvpath = csvpath;
    }


    @Override
    public void addCustomer(CustomerModel c) {

    }

    @Override
    public CustomerModel getCustomerById(String id) {
        return null;
    }

    @Override
    public CustomerModel getCustomerByEmail(String email) {
        return null;
    }

    @Override
    public void removeCustomerById(String id) {

    }

    @Override
    public void updateCustomer(CustomerModel c) {

    }
}
