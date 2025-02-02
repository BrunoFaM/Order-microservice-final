package com.example.order_service.controllers;

import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) throws OrderNotFoundException {
       // orderService.updateOrderStatus(id, status);


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
