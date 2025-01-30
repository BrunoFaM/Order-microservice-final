package com.example.order_service.repository;

import com.example.order_service.models.Order;
import com.example.order_service.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long UserId);

    List<Order> findAllByStatus(OrderStatus status);
}
