package com.microservices;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> getCustomer(int id);

    Flux<Customer> searchCustomers(String nameFilter);

    Mono<Customer> createCustomer(Mono<Customer> customerMono);

}
