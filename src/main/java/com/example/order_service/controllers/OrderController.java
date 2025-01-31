package com.example.order_service.controllers;

import com.example.order_service.config.JwtUtils;
import com.example.order_service.dtos.NewOrder;
import com.example.order_service.dtos.NewOrderItem;
import com.example.order_service.dtos.NewOrderRequest;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.exceptions.UserNotFoundException;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping
    public ResponseEntity<?> getAllOrders(HttpServletRequest request){


        List<OrderDTO> orderList = orderService.getAllOrderFromUserId(jwtUtils.getUserId(request));


        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postAnOrder(HttpServletRequest request, @Valid @RequestBody List<NewOrderItem> newProducts) throws OrderErrorException, UserNotFoundException {

        Long userId = jwtUtils.getUserId(request);

        String email = jwtUtils.getEmail(request);

        //OrderDTO order = orderService.createOrder(userId, email, newProducts);

        OrderDTO order = orderService.createOrder(request, newProducts);

        //return new ResponseEntity<>(order, HttpStatus.CREATED);

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }




}
