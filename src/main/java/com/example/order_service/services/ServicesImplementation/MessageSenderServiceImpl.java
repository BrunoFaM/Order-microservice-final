package com.example.order_service.services.ServicesImplementation;

import com.example.order_service.dtos.OrderReduceStockRequest;
import com.example.order_service.dtos.OrderSendDetailsDTO;
import com.example.order_service.services.MessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderServiceImpl implements MessageSenderService {

    private static final Logger logger = LoggerFactory.getLogger(MessageSenderServiceImpl.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendOrderDetailsMessage(OrderSendDetailsDTO orderDetails) {
        try{
            amqpTemplate.convertAndSend("exchange", "routing.key4", orderDetails);
        } catch (AmqpException e) {
            logger.warn("Something go wrong with Rabbit Server:", e.getMessage());
        }
    }

    @Override
    public void sendReduceStockMessage(OrderReduceStockRequest reduceStockRequest) {
        try{
            amqpTemplate.convertAndSend("exchange", "routing.key", reduceStockRequest);
        } catch (AmqpException e) {
            logger.warn("Something go wrong with Rabbit Server:", e.getMessage());
        }
    }

}
