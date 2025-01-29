package com.example.order_service.dtos;

import java.util.List;

public record OrderReduceStockRequest(Long orderId, List<NewOrderItem> products) {
}
