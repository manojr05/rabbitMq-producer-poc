package com.rabbitmq.service.impl;


import com.rabbitmq.service.RabbitAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitAdminServiceImpl implements RabbitAdminService {

    private final RabbitAdmin rabbitAdmin;

    @Override
    public void registerConsumer(String macAddress) {
        Exchange exchange = new DirectExchange(macAddress);
        rabbitAdmin.declareExchange(exchange);

        Queue messageQueue = new Queue(macAddress+".messageQueue", true, false, false);
        rabbitAdmin.declareQueue(messageQueue);

        Queue acknowledgeQueue = new Queue(macAddress+".acknowledgeQueue", true, false, false);
        rabbitAdmin.declareQueue(acknowledgeQueue);

        Binding messageBinding = BindingBuilder.bind(messageQueue).to(exchange).with("messageRoute").noargs();
        rabbitAdmin.declareBinding(messageBinding);

        Binding acknowledgeBinding = BindingBuilder.bind(acknowledgeQueue).to(exchange).with("acknowledgeRoute").noargs();
        rabbitAdmin.declareBinding(acknowledgeBinding);
    }
}
