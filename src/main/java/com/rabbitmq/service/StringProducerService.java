package com.rabbitmq.service;

public interface StringProducerService {
    void sendStringMessage(String message, String routingKey, String key);
}
