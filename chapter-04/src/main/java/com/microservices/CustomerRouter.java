package com.microservices;

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
    public RouterFunction<ServerResponse> customerRouters() {
        return RouterFunctions.route().nest(RequestPredicates.path("/functional"), builder1 ->
                builder1
                        .nest(RequestPredicates.path("/customer"), builder2 ->
                                builder2
                                        .GET("/{id}", customerHandler::get)
                                        .POST("/", customerHandler::create)
                        )
                        .nest(RequestPredicates.path("/customers"), builder2 ->
                                builder2
                                        .GET("/", customerHandler::search)
                        )
        ).build();
    }

}
