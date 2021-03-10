package com.breader.currencyexchange.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private static final RestTemplate dummyTemplate = new RestTemplate();
    private static final String dummyEndpoint = "localhost:8080/dummy/endpoint";

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @Bulkhead(name = "default")
//    @RateLimiter(name = "default")
//    @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    public String sampleApi() {
//        logger.info("Sample API call received");
//        ResponseEntity<String> forEntity = dummyTemplate.getForEntity(dummyEndpoint, String.class);

        return "Sample API";
    }

    public String hardcodedResponse(Exception e) {
        logger.error(e.getMessage());
        return "fallback response";
    }

}
