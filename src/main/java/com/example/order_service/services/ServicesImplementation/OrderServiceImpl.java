package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.config.JwtUtils;
import com.example.order_service.dtos.*;
import com.example.order_service.exceptions.OrderErrorException;
import com.example.order_service.exceptions.OrderNotFoundException;
import com.example.order_service.exceptions.UserNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.OrderStatus;
import com.example.order_service.repository.OrderItemRepository;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.services.MessageSenderService;
import com.example.order_service.services.OrderService;
import com.example.order_service.services.ProductRequestsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.AmqpTemplate;
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
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ProductRequestsService productRequestsService;



    @Override
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orderList = orderRepository.findAll()
                .stream()
                .map(OrderDTO::new)
                .toList();
        return orderList;
    }

    @Override
    public List<OrderDTO> getAllOrderFromUserId(Long userId) {
        List<OrderDTO> orderList = orderRepository.findAllByUserId(userId)
                .stream()
                .map(OrderDTO::new)
                .toList();
        return orderList;
    }



    @Transactional
    private OrderDTO saveOrder(Long userId, String email ,List<NewOrderItem> newOrderProducts) {
        Order order = new Order(userId, OrderStatus.PENDING);
        Set<OrderItem> products = newOrderProducts
                        .stream()
                        .map(newOrderItem -> new OrderItem(newOrderItem.productId(), newOrderItem.quantity()))
                        .collect(Collectors.toSet());

        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);

        orderItemRepository.saveAll(order.getProducts());

        OrderReduceStockRequest orderReduceStockRequest = new OrderReduceStockRequest(savedOrder.getId(), newOrderProducts);

        System.out.println(orderReduceStockRequest);

        OrderSendDetailsDTO detailsDTO = new OrderSendDetailsDTO(email, newOrderProducts);

        messageSenderService.sendReduceStockMessage(orderReduceStockRequest);

        messageSenderService.sendOrderDetailsMessage(detailsDTO);

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

    public OrderDTO createOrder(HttpServletRequest request, List<NewOrderItem> products) throws OrderErrorException{

        List<NewOrderItem> mergedProducts = this.mergeEqualProducts(products);

        productRequestsService.validateProductsStockAndExistence(mergedProducts);

        String email = jwtUtils.getEmail(request);

        Long userId = jwtUtils.getUserId(request);

        return saveOrder(userId, email,mergedProducts);

    }

    @Override
    @Transactional
    public List<OrderReduceStockRequest> getAllPendingOrders() {
        List<Order> orderList = orderRepository.findAllByStatus(OrderStatus.PENDING);
        List<OrderReduceStockRequest> pendingOrders = new ArrayList<>();

        for(Order order : orderList){
            System.out.println(order.getId());
            List<NewOrderItem> items = order.getProducts()
                    .stream()
                    .map(item -> new NewOrderItem(item.getProductId(), item.getQuantity()))
                    .toList();
            System.out.println(items);
            pendingOrders.add(new OrderReduceStockRequest(order.getId(), items));
        }
        return pendingOrders;
    }


    public Order getOrderById(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException());
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long id) throws OrderNotFoundException {
        Order order = getOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        }

}