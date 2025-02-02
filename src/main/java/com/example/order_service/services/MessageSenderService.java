package com.example.order_service.services;

import com.example.order_service.dtos.OrderReduceStockRequest;
import com.example.order_service.dtos.OrderSendDetailsDTO;

public interface MessageSenderService {

    void sendOrderDetailsMessage(OrderSendDetailsDTO orderDetails);

    void sendReduceStockMessage(OrderReduceStockRequest reduceStockRequest);

}
