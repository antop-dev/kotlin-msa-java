package com.microservices.chapter05;

import org.springframework.http.HttpStatus;
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
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return customerService.createCustomer(serverRequest.bodyToMono(Customer.class))
                .flatMap(it -> ServerResponse.created(URI.create("/customer/" + it.getId())).build());
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return customerService.deleteCustomer(id).flatMap(it -> it ? ServerResponse.ok().build() : ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    public Mono<ServerResponse> search(ServerRequest serverRequest) {
        return ServerResponse.ok().body(customerService.searchCustomers(serverRequest.queryParam("nameFilter").orElse("")), Customer.class);
    }

}
