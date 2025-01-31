package com.example.order_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JsonbMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue queue() {
        return new Queue("testingQueue1", false);
    }

    @Bean
    public Queue queue3() {
        return new Queue("reduceStockScheduled", false);
    }

    @Bean
    public Queue sendOrderDetailQueue(){
        return new Queue("orderDetailsQueue");
    }



    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("reduceStockExchange");
    }

    @Bean
    public TopicExchange exchange3() {
        return new TopicExchange("reduceStockScheduledExchange");
    }

    @Bean
    public Binding bindQueue(Queue sendOrderDetailQueue, TopicExchange exchange){
        return BindingBuilder.bind(sendOrderDetailQueue).to(exchange).with("routing.key4");
    }


    @Bean
    public Binding bindingQueue(Queue queue3, TopicExchange exchange3) {
        return BindingBuilder.bind(queue3).to(exchange3).with("routing.key3");
    }

    @Bean
    public Binding bindingQueue3(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key");
    }



    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}