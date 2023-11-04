package com.example.demo.login;

import com.example.demo.models.appuser.AppUser;
import com.example.demo.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;

    public Optional<AppUser> loginUser(String authenticatedUsername) {
        return userService.findUserByEmail(authenticatedUsername);
    }
}
