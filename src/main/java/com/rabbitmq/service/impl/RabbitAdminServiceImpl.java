package com.rabbitmq.service.impl;


import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.service.RabbitAdminService;
import com.rabbitmq.service.mq.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpIOException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitAdminServiceImpl implements RabbitAdminService {

    @Value("${APPLICATION_NAME}")
    private String appName;

    private final RabbitAdmin rabbitAdmin;

    private final DynamicRabbitListenerConfigurer dynamicRabbitListenerConfigurer;

    @Override
    public String registerConsumer(String storeCode) {
        createQueuesInRabbitMQ(storeCode);
        dynamicRabbitListenerConfigurer.addRabbitListener(appName+".file",
                                                            new MyMessageHandler(),
                                                "fileConsumer");
        log.info("Listener added successfully for queue: "+appName+"-file");
        return appName;
    }

    private void createQueuesInRabbitMQ(String storeCode){

        try{
            Exchange exchange = new TopicExchange(storeCode);

            declareQueueAndBinding(exchange, appName + ".file", "#.file");
            declareQueueAndBinding(exchange, appName + ".statistics", appName + ".statistics");
            declareQueueAndBinding(exchange, appName + ".acknowledgement", appName + ".acknowledgement");
        }catch (AmqpIOException e){
            log.info("Exchange doesn't exist, creating new exchange");
            Exchange exchange = new TopicExchange(storeCode);
            rabbitAdmin.declareExchange(exchange);

            declareQueueAndBinding(exchange, appName + ".file", "#.file");
            declareQueueAndBinding(exchange, appName + ".statistics", appName + ".statistics");
            declareQueueAndBinding(exchange, appName + ".acknowledgement", appName + ".acknowledgement");

        }
    }

    private void declareQueueAndBinding(Exchange exchange, String queueName, String routingKey) throws ShutdownSignalException{
        Queue queue = new Queue(queueName, true, false, false);
        rabbitAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
        rabbitAdmin.declareBinding(binding);
        log.info("Queue added successfully: " + queueName);
    }
}
