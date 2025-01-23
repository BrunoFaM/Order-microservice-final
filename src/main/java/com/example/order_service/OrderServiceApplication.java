package com.example.order_service;

import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OrderServiceApplication {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(){
		return args -> {
			OrderItem item1 = new OrderItem(101L, 2);
			OrderItem item2 = new OrderItem(102L, 1);
			OrderItem item3 = new OrderItem(103L, 4);
			OrderItem item4 = new OrderItem(104L, 3);
			OrderItem item5 = new OrderItem(105L, 5);
			OrderItem item6 = new OrderItem(106L, 2);
			OrderItem item7 = new OrderItem(107L, 1);
			OrderItem item8 = new OrderItem(108L, 3);


			Order order1 = new Order(1L, OrderStatus.PENDING);
			Order order2 = new Order(2L, OrderStatus.COMPLETED);

			orderRepository.saveAll(List.of(order1, order2));

			order1.addOrderItem(item2);
			order1.addOrderItem(item3);
			order1.addOrderItem(item5);
			order1.addOrderItem(item4);
			order1.addOrderItem(item1);
			order2.addOrderItem(item6);
			order2.addOrderItem(item7);
			order2.addOrderItem(item8);

			orderItemRepository.saveAll(List.of(item1, item2, item3, item4, item5, item6, item7, item8));

		};
	}


}
