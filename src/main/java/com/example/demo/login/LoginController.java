package com.example.demo.login;


import com.example.demo.models.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public Optional<AppUser> loginUser() {
        String authenticatedUsername = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return loginService.loginUser(authenticatedUsername);
    }

}
