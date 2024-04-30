package com.aimscore.service.impl;

import com.aimscore.feign.proxy.CMSServiceProxy;
import com.aimscore.feign.proxy.MacServiceProxy;
import com.aimscore.model.Gateway;
import com.aimscore.service.CoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class CoreServiceImpl implements CoreService {

    private final MacServiceProxy macServiceProxy;

    private final CMSServiceProxy cmsServiceProxy;

    private final MongoTemplate repository;

    @Override
    public String registerMac(String uri, String storeCode)  {
        String response = registerMacInGateway(uri, storeCode);

        log.info("Sending request to the CMS to register the listeners for statistics & acknowledgement");
        cmsServiceProxy.addListener(response+".statistics");
        cmsServiceProxy.addListener(response+".acknowledgement");

        log.info("Saving the registered gateway information in the db");
        repository.save(new Gateway(response, uri));

        return response;
    }

    private String registerMacInGateway(String uri, String storeCode){
        URI macURI = URI.create("http://" + uri);
        log.info("Calling the MasterBox to register MQ");
        ResponseEntity<String> macResponse = macServiceProxy.registerConsumer(macURI, storeCode);
        if(macResponse.hasBody()){
            log.info("MasterBox sent the response of the macId: {}", macResponse.getBody());
            return macResponse.getBody();
        }
        return "Error occurred while registering with MAC";
    }
}
