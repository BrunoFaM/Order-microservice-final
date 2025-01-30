package com.example.order_service.controllers;

import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(HttpServletRequest request){

        List<OrderDTO> orderList = orderService.getAllOrders();


        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}
