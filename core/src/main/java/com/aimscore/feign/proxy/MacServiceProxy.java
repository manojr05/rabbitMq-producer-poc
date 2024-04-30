package com.aimscore.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "MACService", url = "http://localhost:8888")
public interface MacServiceProxy {

    @PostMapping("/register/consumer")
    public ResponseEntity<String> registerConsumer(URI uri, @RequestParam String storeCode);

}
