package com.example.order_service.services;

import com.example.order_service.dtos.NewOrder;
import com.example.order_service.dtos.NewOrderItem;
import com.example.order_service.dtos.NewOrderRequest;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.exceptions.UserNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getAllOrderFromUserId(Long userId);

    //OrderDTO createOrder(NewOrder newOrder);

    void updateOrderStatus(Long id, OrderStatus status) throws OrderNotFoundException;

    //public OrderDTO createOrder(NewOrderRequest newOrderRequest) throws OrderErrorException, UserNotFoundException;

    public OrderDTO createOrder(Long userId, List<NewOrderItem> itemsList) throws OrderErrorException;
}