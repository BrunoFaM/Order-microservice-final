package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.dtos.NewOrder;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = orderRepository.findAll()
                .stream()
                .map(OrderDTO::new)
                .toList();
        return orderList;
    }

    @Override
    public OrderDTO createOrder(NewOrder newOrder) {
        Order order = new Order(newOrder.userId(), newOrder.status());
        Set<OrderItem> products = newOrder
                        .products()
                        .stream()
                        .map(newOrderItem -> new OrderItem(newOrderItem.productId(), newOrderItem.quantity()))
                        .collect(Collectors.toSet());

        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(order.getProducts());

        return new OrderDTO(savedOrder);
    }

    Order getOrderById(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException());
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) throws OrderNotFoundException {
        Order order = getOrderById(id);
        if(order.getStatus() != status){
            order.setStatus(status);
            orderRepository.save(order);
        }

    }
}
