package com.microservices.chapter04;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Mono<Customer>> getCustomer(@PathVariable int id) {
        Mono<Customer> customer = customerService.getCustomer(id);
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    @GetMapping("/customers")
    public Flux<Customer> getCustomers(@RequestParam(required = false, defaultValue = "") String nameFilter) {
        return customerService.searchCustomers(nameFilter);
    }

    @PostMapping("/customer")
    public ResponseEntity<Mono<Object>> createCustomer(@RequestBody Mono<Customer> customerMono) {
        return new ResponseEntity(customerService.createCustomer(customerMono), HttpStatus.CREATED);
    }

}
