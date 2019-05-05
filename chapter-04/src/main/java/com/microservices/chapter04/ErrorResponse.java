package com.microservices.chapter04;

import lombok.Data;

@Data
public class ErrorResponse {

    private final String error;
    private final String message;

}
