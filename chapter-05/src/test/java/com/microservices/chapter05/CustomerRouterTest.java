package com.microservices.chapter05;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerRouterTest {
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private CustomerRepository customerRepository;

    @After
    public void tearDown() {
        customerRepository.initializeRepository();
    }

    @Test
    public void get() {
        webClient.get().uri("/customer/2").exchange().expectStatus().isOk()
                .expectBody(Customer.class).isEqualTo(new Customer(2, "Spring"));
    }

    @Test
    public void create() {
        Customer customer = new Customer(4, "New Customer", new Customer.Telephone("+41", "1234567890"));
        webClient.post()
                .uri("/customer")
                .body(Mono.just(customer), Customer.class)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().value("Location", Matchers.equalTo("/customer/" + customer.getId()));
    }

    @Test
    public void delete() {
        int id = 2;
        webClient.delete().uri("/customer/" + id).exchange().expectStatus().isOk();
        webClient.get().uri("/customer/" + id).exchange().expectStatus().isNotFound();
    }

    @Test
    public void customers() {
        List<Customer> list = webClient.get().uri("/customers").exchange().expectStatus().isOk().expectBodyList(Customer.class).returnResult().getResponseBody();
        Assert.assertEquals("Kotlin", list.get(0).getName());
        Assert.assertEquals("Spring", list.get(1).getName());
    }
}