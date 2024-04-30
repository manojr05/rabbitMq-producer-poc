package com.aimscore.feign.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "CMS-Service", url = "http://localhost:9999")
public interface CMSServiceProxy {

    @PostMapping("/add-listener")
    ResponseEntity<Map<String, String>> addListener(@RequestBody String queueName);

}
