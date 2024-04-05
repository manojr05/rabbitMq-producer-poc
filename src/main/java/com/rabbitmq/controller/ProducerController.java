package com.rabbitmq.controller;

import com.rabbitmq.service.RabbitAdminService;
import com.rabbitmq.service.StringProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final RabbitAdminService rabbitAdminService;

    private final StringProducerService stringProducerService;

    @PostMapping("/register/consumer")
    public ResponseEntity<String> registerConsumer(@RequestParam String macAddress){
        rabbitAdminService.registerConsumer(macAddress);
        return ResponseEntity.ok("Gateway registered successfully");
    }

    @PostMapping("/publish/string")
    public ResponseEntity<String> sendMessage(@RequestParam String message,
                                              @RequestParam String routingKey){
        stringProducerService.sendStringMessage(message, routingKey);
        return ResponseEntity.ok("OK");
    }
}
