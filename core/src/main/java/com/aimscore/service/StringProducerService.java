package com.aimscore.service;

public interface StringProducerService {
    void sendStringMessage(String message, String routingKey, String key);
}
