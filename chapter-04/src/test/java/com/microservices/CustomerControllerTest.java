package com.microservices;

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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void getCustomer() {
        webClient.get().uri("/customer/2").exchange().expectStatus().isOk()
                .expectBody(Customer.class).isEqualTo(new Customer(2, "Spring"));
    }

    @Test
    public void getCustomers() {
        List<Customer> list = webClient.get().uri("/customers").exchange().expectStatus().isOk()
                .expectBodyList(Customer.class).returnResult().getResponseBody();
        assertEquals("Kotlin", list.get(0).getName());
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer(4, "New Customer", new Telephone("+41", "1234567890"));
        webClient.post()
                .uri("/customer")
                .body(Mono.just(customer), Customer.class)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange().expectStatus().isCreated();
    }
}