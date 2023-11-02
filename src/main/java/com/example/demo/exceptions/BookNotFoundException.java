package com.example.demo.exceptions;

public class BookNotFoundException extends IllegalArgumentException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
