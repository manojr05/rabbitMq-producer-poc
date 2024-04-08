package com.rabbitmq.controller;

import com.rabbitmq.service.RabbitAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final RabbitAdminService rabbitAdminService;

    @PostMapping("/register/consumer")
    public ResponseEntity<String> registerConsumer(){
        String macID = rabbitAdminService.registerConsumer();
        return ResponseEntity.ok(macID);
    }
}
