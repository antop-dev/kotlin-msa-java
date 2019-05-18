package com.microservices.chapter05;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<Customer> getCustomer(int id);
    Mono<Customer> createCustomer(Mono<Customer> customer);
    Mono<Boolean> deleteCustomer(int id);
    Flux<Customer> searchCustomers(String nameFilter);
}
