package com.example.order_service.exceptions;

public class OrderNotFoundException extends Exception {

    private static final String message = "Order not found";

    public OrderNotFoundException() {
        super(message);
    }
}
