package com.example.online_library.exceptions;

public class BookNotFoundException extends IllegalArgumentException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
