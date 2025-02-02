package com.example.order_service.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue sendOrderDetailQueue(){
        return new Queue("orderDetailsQueue");
    }

    @Bean
    public Queue queue(){
        return new Queue("reduceStockQueue");
    }



    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }


    @Bean
    public Binding bindQueue(Queue sendOrderDetailQueue, TopicExchange exchange){
        return BindingBuilder.bind(sendOrderDetailQueue).to(exchange).with("routing.key4");
    }



    @Bean
    public Binding bindingQueue2(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key");
    }



    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}