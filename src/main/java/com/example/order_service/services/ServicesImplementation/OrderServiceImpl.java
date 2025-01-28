package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.dtos.NewOrderItem;
import com.example.order_service.dtos.NewOrderRequest;
import com.example.order_service.dtos.OrderDTO;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.exceptions.UserNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${userservice.path}")
    private String urlUserService;
    @Value("${productservice.path}")
    private String urlProductService;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = orderRepository.findAll()
                .stream()
                .map(OrderDTO::new)
                .toList();
        return orderList;
    }

    private OrderDTO saveOrder(Long userId, NewOrderRequest newOrder) {
        Order order = new Order(userId, OrderStatus.PENDING);
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

    private List<NewOrderItem> mergeEqualProducts(List<NewOrderItem> products){
        HashMap<Long , NewOrderItem> uniqueProducts = new HashMap<>();
        //create a hashMap, where the products with the same id are merged in one product with the stock of each one added
        for (NewOrderItem productItem : products) {
            if(!uniqueProducts.containsKey(productItem.productId())) {
                uniqueProducts.put(productItem.productId(), productItem);
            }else {
                NewOrderItem product = uniqueProducts.get(productItem.productId());
                NewOrderItem newProduct = new NewOrderItem(product.productId(), product.quantity() + productItem.quantity());
                uniqueProducts.replace(product.productId(), newProduct);
            }
        }
        return uniqueProducts.values().stream().toList();
    }


    public OrderDTO createOrder(NewOrderRequest newOrderRequest) throws OrderErrorException, UserNotFoundException{
        //getting the user_Id
        String email = newOrderRequest.email();
        ResponseEntity<Long> response;
        //i need a new instance because NewOrderRequest is a record
        NewOrderRequest mergedProductsOrder;
        try {
            response = restTemplate.getForEntity(urlUserService + "/{email}", Long.class, email);
        } catch (HttpClientErrorException exception) {
            throw new UserNotFoundException(exception.getResponseBodyAsString());
        }


        try{
            mergedProductsOrder = new NewOrderRequest(newOrderRequest.email(), this.mergeEqualProducts(newOrderRequest.products()));
            restTemplate.postForEntity(urlProductService + "order", mergedProductsOrder.products(), Boolean.class);
        }catch (HttpStatusCodeException exception){
            throw new OrderErrorException(exception.getResponseBodyAsString());
        }
        Long userId = response.getBody();



        return saveOrder(userId, mergedProductsOrder);

    }

    public Order getOrderById(Long id) throws OrderNotFoundException {
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