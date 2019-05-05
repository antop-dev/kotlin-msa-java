package com.microservices.chapter04;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> getCustomer(int id);

    Flux<Customer> searchCustomers(String nameFilter);

    Mono<?> createCustomer(Mono<Customer> customerMono);

    void reset();
}
