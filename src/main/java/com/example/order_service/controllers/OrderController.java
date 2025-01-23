package com.example.order_service.controllers;

import com.example.order_service.dtos.NewOrder;
import com.example.order_service.dtos.NewOrderRequest;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(){

        List<OrderDTO> orderList = orderService.getAllOrders();


        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postAnOrder(@Valid @RequestBody NewOrderRequest newOrderRequest){

        orderService.createOrder(newOrderRequest);

        return new ResponseEntity<>(HttpStatus.OK);



    }


    @PutMapping ("/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) throws OrderNotFoundException {
        orderService.updateOrderStatus(id, status);


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
