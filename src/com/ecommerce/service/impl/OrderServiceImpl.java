package com.ecommerce.service.impl;

import com.ecommerce.models.OrderModel;
import com.ecommerce.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public OrderModel checkout(String customerId) {
        return null;
    }

    @Override
    public void cancelOrder(String orderId) {

    }

    @Override
    public List<OrderModel> getOrdersForCustomer(String customerId) {
        return List.of();
    }
}
