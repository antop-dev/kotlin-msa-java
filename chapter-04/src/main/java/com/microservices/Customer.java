package com.microservices;

import lombok.Data;

@Data
public class Customer {
    private int id = 0;
    private String name = "";
    private Telephone telephone;

    public Customer() {
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(int id, String name, Telephone telephone) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
    }



}
