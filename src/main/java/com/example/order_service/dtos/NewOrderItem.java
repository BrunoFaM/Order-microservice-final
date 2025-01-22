package com.example.order_service.dtos;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NewOrderItem( @PositiveOrZero(message = "Can't be negative") Long productId,@Positive(message = "Has to be positive") Integer quantity) {
}
