package com.example.online_library.login;


import com.example.online_library.models.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Optional<AppUser>> loginUser() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (authenticatedUsername == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<AppUser> logged = loginService.loginUser(authenticatedUsername);
        if (logged.isEmpty()) {
            return ResponseEntity.ok(Optional.empty());
        }

        return ResponseEntity.ok(logged);
    }

}
