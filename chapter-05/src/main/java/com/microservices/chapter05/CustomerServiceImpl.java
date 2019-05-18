package com.microservices.chapter05;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<Customer> getCustomer(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customer) {
        return customerRepository.create(customer);
    }

    @Override
    public Mono<Boolean> deleteCustomer(int id) {
        return customerRepository.deleteById(id).map(it -> it.getDeletedCount() > 0);
    }

    @Override
    public Flux<Customer> searchCustomers(String nameFilter) {
        return customerRepository.findCustomer(nameFilter);
    }
}
