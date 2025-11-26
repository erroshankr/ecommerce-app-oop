package com.ecommerce.repo;

import com.ecommerce.models.CustomerModel;

public interface CustomerRepository {

    void addCustomer(CustomerModel c);
    CustomerModel getCustomerById(String id);
    CustomerModel getCustomerByEmail(String email);
    void removeCustomerById(String id);
    void updateCustomer(CustomerModel c);
}
