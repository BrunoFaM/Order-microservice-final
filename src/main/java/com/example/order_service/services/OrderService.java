package com.example.order_service.services;

import com.example.order_service.dtos.NewOrder;
import com.example.order_service.dtos.NewOrderRequest;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO createOrder(NewOrder newOrder);

    void updateOrderStatus(Long id, OrderStatus status) throws OrderNotFoundException;

    public void createOrder(NewOrderRequest newOrderRequest);

}