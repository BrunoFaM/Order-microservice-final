package com.example.order_service.services;

import com.example.order_service.dtos.OrderReduceStockRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Scheduled(cron = "*/30 * * * * *")
    public void sendPendingOrdersToBroker(){
        System.out.println("IN THE SCHEDULER");
        System.out.println(new Date());
        List<OrderReduceStockRequest> pendingOrders = orderService.getAllPendingOrders();
        for(OrderReduceStockRequest request : pendingOrders){
            amqpTemplate.convertAndSend("reduceStockScheduledExchange", "routing.key3", request);
        }
    }
}
