package com.example.order_service.services.listeners;

import com.example.order_service.dtos.ReduceStockResponse;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.services.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    @Autowired
    private OrderService orderService;

    //this event update the state of an orden
    @RabbitListener(queues = "updateOrderStatusQueue", concurrency = "1")
    public void listenerQueue2(ReduceStockResponse response) throws OrderNotFoundException {
        if(response.isReduced()){
            orderService.updateOrderStatus(response.orderId());
        }
    }

}
