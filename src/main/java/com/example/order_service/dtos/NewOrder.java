package com.example.order_service.dtos;

import com.example.order_service.models.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record NewOrder(@PositiveOrZero(message = "Can't be negative") Long userId, List<NewOrderItem> products,@NotNull(message = "Can't be null") OrderStatus status) {
}
