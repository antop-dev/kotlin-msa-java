package com.microservices;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public class CustomerServiceImpl implements CustomerService {

    private static final List<Customer> initialCustomers = Arrays.asList(
            new Customer(1, "Kotlin"),
            new Customer(2, "Spring"),
            new Customer(3, "Microservice", new Telephone("+44", "7123456789"))
    );


    private final ConcurrentHashMap<Integer, Customer> customers =
            new ConcurrentHashMap<>(initialCustomers.stream().collect(toMap(Customer::getId, Function.identity())));

    @Override
    public Mono<Customer> getCustomer(int id) {
        return Mono.justOrEmpty(customers.get(id));
    }

    @Override
    public Flux<Customer> searchCustomers(String nameFilter) {
        return Flux.fromStream(
                customers.entrySet().stream()
                        .filter(it -> StringUtils.containsIgnoreCase(it.getValue().getName(), nameFilter))
                        .map(Map.Entry::getValue)
        );
    }

    @Override
    public Mono<Customer> createCustomer(Mono<Customer> customerMono) {
        return customerMono.map(it -> {
            customers.put(it.getId(), it);
            return it;
        });
    }

}
