package com.example.order_service.services;

import com.example.order_service.dtos.OrderReduceStockRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Scheduled(cron = "*/10 * * * * *")
    public void sendPendingOrdersToBroker(){
        System.out.println("IN THE SCHEDULER");
        List<OrderReduceStockRequest> pendingOrders = orderService.getAllPendingOrders();
        for(OrderReduceStockRequest request : pendingOrders){
            amqpTemplate.convertAndSend("reduceStockExchange", "routing.key", request);
        }
    }
}
