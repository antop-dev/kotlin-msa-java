package com.microservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
