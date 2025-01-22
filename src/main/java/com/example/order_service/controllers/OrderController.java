package com.example.order_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<?> getAllOrders(){


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postAnOrder(){

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateOrderStatus(){

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
