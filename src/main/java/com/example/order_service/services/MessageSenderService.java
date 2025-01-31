package com.example.order_service.services;

import com.example.order_service.dtos.OrderSendDetailsDTO;

public interface MessageSenderService {

    void sendReduceStock(OrderSendDetailsDTO orderDetails);

}
