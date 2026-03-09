package com.spunit.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/internal/load-balancer")
public class LoadBalancerInfoController {

    private final Environment environment;

    public LoadBalancerInfoController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", environment.getProperty("spring.application.name"));
        response.put("strategy", "round-robin");
        response.put("discoveryEnabled", environment.getProperty("eureka.client.enabled", "true"));
        response.put("activeProfiles", environment.getActiveProfiles());
        response.put("routes", new String[]{"/api/users/**", "/api/products/**", "/api/orders/**"});
        return response;
    }
}

