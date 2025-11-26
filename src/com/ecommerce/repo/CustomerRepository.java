package com.ecommerce.repo;

import com.ecommerce.exceptions.CustomerAlreadyExistException;
import com.ecommerce.exceptions.CustomerNotFoundException;
import com.ecommerce.models.CustomerModel;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {

    void addCustomer(CustomerModel c) throws IOException, CustomerAlreadyExistException;
    CustomerModel getCustomerById(String id) throws IOException;
    CustomerModel getCustomerByEmail(String email) throws IOException;
    void removeCustomerById(String id) throws IOException, CustomerNotFoundException;
    void updateCustomer(CustomerModel c) throws IOException, CustomerNotFoundException;
    List<CustomerModel> findAll() throws IOException;
}
