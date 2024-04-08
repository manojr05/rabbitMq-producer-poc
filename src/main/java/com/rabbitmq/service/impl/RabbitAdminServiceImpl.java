package com.rabbitmq.service.impl;


import com.rabbitmq.service.RabbitAdminService;
import com.rabbitmq.service.mq.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String registerConsumer() {
        createQueuesInRabbitMQ();
        dynamicRabbitListenerConfigurer.addRabbitListener(appName+"-file",
                                                            new MyMessageHandler(),
                                                "handleMessage");
        log.info("Listener added successfully for queue: "+appName+"-file");
        return appName;
    }

    private void createQueuesInRabbitMQ(){
        Exchange exchange = new DirectExchange(appName);
        rabbitAdmin.declareExchange(exchange);
        log.info("Exchange added successfully: "+appName);

        Queue fileQueue = new Queue(appName+"-file", true, false, false);
        rabbitAdmin.declareQueue(fileQueue);
        Binding fileBinding = BindingBuilder.bind(fileQueue).to(exchange).with(appName+".file").noargs();
        rabbitAdmin.declareBinding(fileBinding);
        log.info("Queue added successfully: "+appName+"-file");

        Queue statisticsQueue = new Queue(appName+"-statistics", true, false, false);
        rabbitAdmin.declareQueue(statisticsQueue);
        Binding statisticsBinding = BindingBuilder.bind(statisticsQueue).to(exchange).with(appName+".statistics").noargs();
        rabbitAdmin.declareBinding(statisticsBinding);
        log.info("Queue added successfully: "+appName+"-statistics");

        Queue acknowledgementQueue = new Queue(appName+"-acknowledgement", true, false, false);
        rabbitAdmin.declareQueue(acknowledgementQueue);
        Binding acknowledgementBinding = BindingBuilder.bind(acknowledgementQueue).to(exchange).with(appName+".acknowledgement").noargs();
        rabbitAdmin.declareBinding(acknowledgementBinding);
        log.info("Queue added successfully: "+appName+"-acknowledgement");
    }
}
