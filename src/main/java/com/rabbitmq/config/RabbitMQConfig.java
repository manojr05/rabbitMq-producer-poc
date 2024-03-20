package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.string.queue}")
    private String stringQueue;

    @Value("${rabbitmq.string.routingkey}")
    private String stringRoutingKey;

    @Value("${rabbitmq.json.queue}")
    private String jsonQueue;

    @Value("${rabbitmq.json.routingkey}")
    private String jsonRoutingKey;

    @Value("${rabbitmq.file.queue}")
    private String fileQueue;

    @Value("${rabbitmq.file.routingkey}")
    private String fileRoutingKey;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue getStringQueue(){
        return new Queue(stringQueue);
    }

    @Bean
    public Binding stringQueueBinding(){
        return BindingBuilder.bind(getStringQueue())
                .to(topicExchange())
                .with(stringRoutingKey);
    }

    @Bean
    public Queue getJsonQueue(){
        return new Queue(jsonQueue);
    }

    @Bean
    public Binding jsonQueueBinding(){
        return BindingBuilder.bind(getJsonQueue())
                .to(topicExchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public Queue getFileQueue(){
        return new Queue(fileQueue);
    }

    @Bean
    public Binding fileQueueBinding(){
        return BindingBuilder.bind(getFileQueue())
                .to(topicExchange())
                .with(fileRoutingKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
