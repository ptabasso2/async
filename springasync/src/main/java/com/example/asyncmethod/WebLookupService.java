package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class WebLookupService {

    private static final Logger logger = LoggerFactory.getLogger(WebLookupService.class);

    private final RestTemplate restTemplate;

    public WebLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<String> getURL(String url) throws InterruptedException {
        logger.info("Looking up " + url);
        String httpurl = String.format(url);
        String results = restTemplate.getForObject(httpurl, String.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

}