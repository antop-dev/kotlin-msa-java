package com.microservices.chapter05;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class CustomerRouter {
    private final CustomerHandler customerHandler;

    public CustomerRouter(CustomerHandler customerHandler) {
        this.customerHandler = customerHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/customer/{id}"), customerHandler::get)
                .and(RouterFunctions.route(RequestPredicates.POST("/customer"), customerHandler::create))
                .and(RouterFunctions.route(RequestPredicates.DELETE("/customer/{id}"), customerHandler::delete))
                .and(RouterFunctions.route(RequestPredicates.GET("/customers"), customerHandler::search))
                ;
    }
}
