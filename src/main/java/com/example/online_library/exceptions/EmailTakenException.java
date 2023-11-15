package com.example.online_library.exceptions;

public class EmailTakenException extends ValidationException {

    public EmailTakenException(String message) {
        super(message);
    }
}
