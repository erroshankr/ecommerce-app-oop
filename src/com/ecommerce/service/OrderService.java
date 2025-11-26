package com.ecommerce.service;

import com.ecommerce.models.OrderModel;

import java.util.List;

public interface OrderService {

    OrderModel checkout(String customerId);
    void cancelOrder(String orderId);
    List<OrderModel> getOrdersForCustomer(String customerId);

}
