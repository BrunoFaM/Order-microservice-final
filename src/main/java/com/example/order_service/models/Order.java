package com.example.order_service.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OrderEntity")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToMany(mappedBy = "orderId")
    private Set<OrderItem> products = new HashSet<>();

    private OrderStatus status;

    public Order() {
    }

    public Order(Long userId, OrderStatus status) {
        this.userId = userId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void addOrderItem(OrderItem item){
        item.setOrderId(this);
        products.add(item);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderItem> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products +
                ", status=" + status +
                '}';
    }
}
