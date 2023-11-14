package com.example.demo.validators;

import com.example.demo.exceptions.EmailValidationException;
import com.example.demo.exceptions.PasswordValidationException;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PasswordValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        if (s == null || s.isEmpty()) {
            throw new PasswordValidationException("You have not provided a password");
        }

        if (!s.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new PasswordValidationException("Password needs at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, 1 special symbol");
        }

        return true;
    }
}
