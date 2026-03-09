package com.spunit;

import com.spunit.config.WeightedLoadBalancerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

@SpringBootApplication
@LoadBalancerClients(defaultConfiguration = WeightedLoadBalancerConfiguration.class)
public class LoadBalancerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerServiceApplication.class, args);
    }
}
