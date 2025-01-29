package com.example.order_service.controllers;
import com.example.order_service.dtos.NewOrderItem;
import com.example.order_service.dtos.OrderItemDTO;
import com.example.order_service.models.OrderItem;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/test")
public class RabbitTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping
    public ResponseEntity<String> getMessage(){
        NewOrderItem testItem = new NewOrderItem(1L, 23);
        amqpTemplate.convertAndSend("testingExchange", "routing.key", testItem);
        return ResponseEntity.ok("Message received");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getMessage(@PathVariable Long id){
        amqpTemplate.convertAndSend("testingExchange", "routing.key2", id);
        return ResponseEntity.ok("Id received");
    }

//    @GetMapping("/signal")
//    public ResponseEntity<String> getMessage(){
//        amqpTemplate.convertAndSend("testingExchange", "routing.key3", "Signal recived");
//        return ResponseEntity.ok("Message received");
//    }

}