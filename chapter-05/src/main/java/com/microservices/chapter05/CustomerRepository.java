package com.microservices.chapter05;

import com.mongodb.client.result.DeleteResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Repository
public class CustomerRepository {
    private final List<Customer> initialCustomers = Arrays.asList(
            new Customer(1, "Kotlin"),
            new Customer(2, "Spring"),
            new Customer(3, "Microservice", new Customer.Telephone("+44", "7123456789"))
    );

    private final ReactiveMongoTemplate template;

    public CustomerRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @PostConstruct
    public void initializeRepository() {
        template.remove(Query.query(Criteria.where("_id").lte(4)), Customer.class).subscribe();
        initialCustomers.stream().map(Mono::just).map(this::create).forEach(Mono::subscribe);
    }

    public Mono<Customer> create(Mono<Customer> customer) {
        return template.save(customer);
    }

    public Mono<Customer> findById(int id) {
        return template.findById(id, Customer.class);
    }

    public Mono<DeleteResult> deleteById(int id) {
        return template.remove(Query.query(Criteria.where("_id").is(id)), Customer.class);
    }

    public Flux<Customer> findCustomer(String nameFilter) {
        return template.find(Query.query(Criteria.where("name").regex(".*" + nameFilter + ".*", "i")).with(Sort.by(Sort.Order.asc("id"))), Customer.class);
    }

}
