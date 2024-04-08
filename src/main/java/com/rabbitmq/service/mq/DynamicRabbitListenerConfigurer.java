package com.rabbitmq.service.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@Component
public class DynamicRabbitListenerConfigurer implements RabbitListenerConfigurer {

    private RabbitListenerEndpointRegistrar registrar;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        this.registrar = registrar;
    }

    public void addRabbitListener(String queueName, Object bean, String methodName) {
        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
        endpoint.setId(queueName);
        endpoint.setQueueNames(queueName);
        endpoint.setMessageListener(new CustomRabbitListenerHandler(bean, methodName));
        registrar.registerEndpoint(endpoint);
    }

}

