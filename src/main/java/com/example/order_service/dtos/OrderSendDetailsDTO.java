package com.example.order_service.dtos;

import java.util.List;

public record OrderSendDetailsDTO (String email, List<NewOrderItem> products) {
}
