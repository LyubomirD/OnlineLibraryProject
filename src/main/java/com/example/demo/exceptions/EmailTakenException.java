package com.example.demo.exceptions;

public class EmailTakenException extends ValidationException {

    public EmailTakenException(String message) {
        super(message);
    }
}
