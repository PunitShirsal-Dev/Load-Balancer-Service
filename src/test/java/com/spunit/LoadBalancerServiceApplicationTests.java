package com.spunit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.gateway.discovery.locator.enabled=false",
        "spring.cloud.discovery.client.simple.instances.user-service[0].uri=http://localhost:65531",
        "spring.cloud.discovery.client.simple.instances.user-service[1].uri=http://localhost:65532"
})
class LoadBalancerServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}

