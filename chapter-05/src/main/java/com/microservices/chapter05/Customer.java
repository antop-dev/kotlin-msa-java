package com.microservices.chapter05;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customers")
@Data
public class Customer {
    private int id;
    private String name;
    private Telephone telephone;

    public Customer() {
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(int id, String name, Telephone telephone) {
        this(id, name);
        this.telephone = telephone;
    }

    @Data
    public static class Telephone {
        private String countryCode;
        private String telephoneNumber;

        public Telephone() {
        }

        public Telephone(String countryCode, String telephoneNumber) {
            this.countryCode = countryCode;
            this.telephoneNumber = telephoneNumber;
        }
    }
}
