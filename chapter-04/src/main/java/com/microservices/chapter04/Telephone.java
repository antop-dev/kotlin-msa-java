package com.microservices.chapter04;

import lombok.Data;

@Data
public class Telephone {
    private String countryCode;
    private String telephoneNumber;

    public Telephone(String countryCode, String telephoneNumber) {
        if (countryCode == null) {
            countryCode = "";
        }
        if (telephoneNumber == null) {
            telephoneNumber = "";
        }
        this.countryCode = countryCode;
        this.telephoneNumber = telephoneNumber;
    }
}
