package com.example.order_service.exceptions;

public class OrderErrorException extends RuntimeException {
    public OrderErrorException(String message) {
        super(message);
    }
}
