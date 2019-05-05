package com.microservices.chapter04;

public class CustomerExistException extends Exception {

    public CustomerExistException(String message) {
        super(message);
    }

}
