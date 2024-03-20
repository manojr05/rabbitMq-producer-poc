package com.rabbitmq.controller;

import com.rabbitmq.model.Employee;
import com.rabbitmq.service.FileProducerService;
import com.rabbitmq.service.JsonProducerService;
import com.rabbitmq.service.StringProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final StringProducerService stringProducerService;

    private final JsonProducerService jsonProducerService;

    private final FileProducerService fileProducerService;

    @PostMapping("/publish/string")
    public ResponseEntity<String> sendMessage(@RequestParam String message){
        return ResponseEntity.ok(stringProducerService.sendStringMessage(message));
    }

    @PostMapping("/publish/json")
    public ResponseEntity<Employee> sendMessage(@RequestBody Employee employee){
        return ResponseEntity.ok(jsonProducerService.sendJsonMessage(employee));
    }

    @PostMapping("/publish/file")
    public ResponseEntity<String> sendMessage(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(fileProducerService.sendFileMessage(file));
    }

}
