package com.example.online_library.login;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        String[] credentials = getCredentials(authHeader);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String password = credentials[1];

            if (loginService.authenticateUser(username, password)) {
                return ResponseEntity.ok("{\"message\":\"Login successful\"}");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Invalid credentials\"}");
    }

    private String[] getCredentials(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            byte[] base64Credentials = authHeader.substring("Basic ".length()).getBytes(StandardCharsets.UTF_8);
            byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);
            return credentials.split(":", 2);
        }
        return null;
    }
}
