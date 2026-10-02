package com.ecommerce.Exception;

public class APIException extends RuntimeException{
    private static final Long serailVersionUID = 1l;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }
}
