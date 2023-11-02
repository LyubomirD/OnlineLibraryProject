package com.example.demo.registration;

import com.example.demo.exceptions.EmailValidationException;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        if (s == null || s.isEmpty()) {
            throw new EmailValidationException("You have not provided an email address");
        }

        if (!s.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.+[A-Za-z0-9.-]+")) {
            throw new EmailValidationException("Incorrectly written email");
        }

        return true;
    }
}
