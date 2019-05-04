package com.microservices;

import org.hamcrest.Matchers;
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
public class CustomerRouterTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void get() {
        webClient.get().uri("/functional/customer/2").exchange().expectStatus().isOk()
                .expectBody(Customer.class).isEqualTo(new Customer(2, "Spring"));
    }

    @Test
    public void search() {
        List<Customer> list = webClient.get().uri("/functional/customers").exchange().expectStatus().isOk()
                .expectBodyList(Customer.class).returnResult().getResponseBody();
        assertEquals("Spring", list.get(1).getName());
    }

    @Test
    public void create() {
        Customer customer = new Customer(4, "New Customer", new Telephone("+41", "1234567890"));
        webClient.post()
                .uri("/functional/customer")
                .body(Mono.just(customer), Customer.class)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().value("Location", Matchers.equalTo("/functional/customer/" + customer.getId()));
    }
}