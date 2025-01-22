package com.example.order_service.dtos;

import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import jakarta.persistence.ManyToOne;

public class OrderItemDTO {

    private final Long id;

    private final Long orderId;

    private final Long productId;

    private final Integer quantity;

    public OrderItemDTO(OrderItem orderItem){
        id = orderItem.getId();
        orderId = orderItem.getOrderId().getId();
        productId = orderItem.getProductId();
        quantity = orderItem.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
