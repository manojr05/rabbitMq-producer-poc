package com.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProducerService {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.file.routingkey}")
    private String fileRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public String sendFileMessage(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String encryptedFile = Base64.getEncoder().encodeToString(bytes);
            rabbitTemplate.convertAndSend(exchange, fileRoutingKey, encryptedFile);
            log.info("Sent file as a message");
        } catch (IOException e) {
            log.error("Failed to convert the file to byte array");
            return "Not able to send the file";
        }catch (Exception e){
            log.error(e.getMessage());
            return "Not able to send the file";
        }
        return "Sent file as a message";
    }
}
