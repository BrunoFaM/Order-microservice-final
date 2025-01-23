package com.example.order_service.dtos;

import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.OrderStatus;
import java.util.List;


public class OrderDTO {

    private final Long id;

    private final Long userId;

    private final List<OrderItemDTO> products;

    private final OrderStatus status;

    public OrderDTO(Order order){
        id = order.getId();
        userId = order.getUserId();
        status = order.getStatus();
        products = order.getProducts()
                .stream()
                .map(OrderItemDTO::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemDTO> getProducts() {
        return products;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
