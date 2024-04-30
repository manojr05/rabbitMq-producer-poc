package com.aimscore.controller;

import com.aimscore.service.CoreService;
import com.aimscore.service.StringProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CoreController {

    private final CoreService service;

    private final StringProducerService stringProducerService;

    @PostMapping("/register/mac")
    public ResponseEntity<String> registerMac(@RequestParam String uri, @RequestParam String storeCode) {
        log.info("Received request to register mac with host: {}", uri);
        return ResponseEntity.ok(service.registerMac(uri, storeCode));
    }

    @PostMapping("/publish/string")
    public ResponseEntity<String> sendMessage(@RequestParam String message,
                                              @RequestParam String exchange,
                                              @RequestParam String routingKey){
        stringProducerService.sendStringMessage(exchange, message, routingKey);
        return ResponseEntity.ok(message);
    }
}
