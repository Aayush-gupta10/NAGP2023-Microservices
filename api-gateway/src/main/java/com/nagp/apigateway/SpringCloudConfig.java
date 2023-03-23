package com.nagp.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/master-data-service/**")
                        .uri("lb://master-data-service")
                        )

                .route(r -> r.path("/search-service/**")
                        .uri("lb://search-service")
                        )
                .build();
    }
}
