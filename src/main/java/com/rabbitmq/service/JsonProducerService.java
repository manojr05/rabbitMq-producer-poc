package com.rabbitmq.service;

import com.rabbitmq.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JsonProducerService {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.json.routingkey}")
    private String jsonRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public Employee sendJsonMessage(Employee employee){
        rabbitTemplate.convertAndSend(exchange, jsonRoutingKey, employee);
        log.info("Sent json message: {}", employee.toString());
        return employee;
    }

}
