package com.example.order_service.services;

import com.example.order_service.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

}
