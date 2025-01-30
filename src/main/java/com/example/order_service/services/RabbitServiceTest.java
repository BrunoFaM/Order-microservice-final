package com.example.order_service.services;

import com.example.order_service.dtos.ReduceStockResponse;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.OrderStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RabbitServiceTest {

    @Autowired
    private OrderService orderService;

    //this event update the state of an orden to completed or cancelled
    @RabbitListener(queues = "testingQueue2", concurrency = "1")
    public void listenerQueue2(ReduceStockResponse response) throws OrderNotFoundException {
        System.out.println("LISTENER ORDER SERVICE");
        if(response.isReduced()){
            orderService.updateOrderStatus(response.orderId(), OrderStatus.COMPLETED);
        }else{
            orderService.updateOrderStatus(response.orderId(), OrderStatus.FAILED);
        }
        ;
        System.out.println("****************");
    }



}
