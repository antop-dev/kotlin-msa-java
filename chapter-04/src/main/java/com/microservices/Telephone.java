package com.microservices;

import lombok.Data;

@Data
public class Telephone {
    private String countryCode = "";
    private String telephoneNumber = "";

    public Telephone() {
    }

    public Telephone(String countryCode, String telephoneNumber) {
        this.countryCode = countryCode;
        this.telephoneNumber = telephoneNumber;
    }
}
