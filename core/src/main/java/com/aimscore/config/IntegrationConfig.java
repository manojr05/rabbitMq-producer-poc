package com.aimscore.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileSystemPersistentAcceptOnceFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.metadata.SimpleMetadataStore;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Configuration
@Slf4j
@RequiredArgsConstructor
@EnableIntegration
public class IntegrationConfig {

    @Value("${watcher.directory}")
    String watcherDirectory;

    private final RabbitTemplate rabbitTemplate;

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "2000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        CompositeFileListFilter<File> filter = new CompositeFileListFilter<>();
        filter.addFilters(new SimplePatternFileListFilter("*.csv"));
        FileSystemPersistentAcceptOnceFileListFilter fileFilter = new FileSystemPersistentAcceptOnceFileListFilter(
                new SimpleMetadataStore(), "");
        filter.addFilters(fileFilter);
        FileReadingMessageSource reader = new FileReadingMessageSource();
        reader.setDirectory(new File(watcherDirectory));
        reader.setFilter(filter);
        return reader;
    }

    @ServiceActivator(inputChannel = "fileInputChannel")
    public void handleFileWrite(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String encryptedFile = Base64.getEncoder().encodeToString(bytes);
            rabbitTemplate.convertAndSend("SuperStore", "all.file", encryptedFile);
            log.info("Sent file as a message");
        } catch (IOException e) {
            log.error("Failed to convert the file to byte array");
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}

