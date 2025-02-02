package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.dtos.OrderReduceStockRequest;
import com.example.order_service.services.MessageSenderService;
import com.example.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledServiceImpl {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageSenderService messageSenderService;

    @Scheduled(cron = "*/30 * * * * *")
    public void sendPendingOrdersToBroker(){
        List<OrderReduceStockRequest> pendingOrders = orderService.getAllPendingOrders();
        for(OrderReduceStockRequest request : pendingOrders){
            messageSenderService.sendReduceStockMessage(request);
        }
    }
}
