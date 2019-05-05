package com.microservices.chapter04;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CustomerHandler {

    private final CustomerService customerService;

    public CustomerHandler(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return customerService.getCustomer(id)
                .flatMap(it -> ServerResponse.ok().body(BodyInserters.fromObject(it)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> search(ServerRequest serverRequest) {
        return ServerResponse.ok().body(
                customerService.searchCustomers(
                        serverRequest.queryParam("nameFilter").orElse("")
                )
                , Customer.class
        );
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return customerService
                .createCustomer(serverRequest.bodyToMono(Customer.class))
                .flatMap(it -> {
                    URI uri = URI.create("/functional/customer/" + ((Customer) it).getId());
                    return ServerResponse.created(uri).build();
                })
                .onErrorResume(Exception.class, it -> ServerResponse.badRequest().body(
                        BodyInserters.fromObject(
                                new ErrorResponse("error creating customer", it.getMessage() != null ? it.getMessage() : "error")
                        )
                ));
    }

}
