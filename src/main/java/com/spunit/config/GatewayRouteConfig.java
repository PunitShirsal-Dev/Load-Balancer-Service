package com.spunit.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;

@Configuration
public class GatewayRouteConfig {

    private static final String REWRITE_SEGMENT = "/${segment}";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-lb", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .rewritePath("/api/users/(?<segment>.*)", REWRITE_SEGMENT)
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(300), 2, false))
                                .addResponseHeader("X-LB-Service", "user-service"))
                        .uri("lb://user-service"))
                .route("product-service-lb", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .rewritePath("/api/products/(?<segment>.*)", REWRITE_SEGMENT)
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(300), 2, false))
                                .addResponseHeader("X-LB-Service", "product-service"))
                        .uri("lb://product-service"))
                .route("order-service-lb", r -> r
                        .path("/api/orders/**")
                        .filters(f -> f
                                .rewritePath("/api/orders/(?<segment>.*)", REWRITE_SEGMENT)
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(300), 2, false))
                                .addResponseHeader("X-LB-Service", "order-service"))
                        .uri("lb://order-service"))
                .route("auth-service-lb", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .rewritePath("/api/auth/(?<segment>.*)", REWRITE_SEGMENT)
                                .addResponseHeader("X-LB-Service", "auth-service"))
                        .uri("lb://auth-service"))
                .route("admin-service-lb", r -> r
                        .path("/api/admin/**")
                        .filters(f -> f
                                .rewritePath("/api/admin/(?<segment>.*)", REWRITE_SEGMENT)
                                .addResponseHeader("X-LB-Service", "admin-service"))
                        .uri("lb://admin-service"))
                .build();
    }
}
