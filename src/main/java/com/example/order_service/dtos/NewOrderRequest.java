package com.example.order_service.dtos;

import java.util.List;

public record NewOrderRequest(String email, List<NewOrderItem> products) {
}
