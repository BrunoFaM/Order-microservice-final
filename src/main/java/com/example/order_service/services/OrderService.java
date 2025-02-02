package com.example.order_service.services;

import com.example.order_service.dtos.*;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.exceptions.UserNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderStatus;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getAllOrderFromUserId(Long userId);

    //OrderDTO createOrder(NewOrder newOrder);

    void updateOrderStatus(Long id) throws OrderNotFoundException;

    //public OrderDTO createOrder(NewOrderRequest newOrderRequest) throws OrderErrorException, UserNotFoundException;

    //public OrderDTO createOrder(Long userId,String email ,List<NewOrderItem> itemsList) throws OrderErrorException;

    public OrderDTO createOrder(HttpServletRequest request, List<NewOrderItem> itemsList) throws OrderErrorException;

    public List<OrderReduceStockRequest> getAllPendingOrders();
}