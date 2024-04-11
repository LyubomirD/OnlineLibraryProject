package com.example.online_library.login;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader) {
        // Parse the Authorization header to extract username and password
        String[] credentials = parseCredentials(authHeader);
        if (credentials == null || credentials.length != 2) {
            return ResponseEntity.badRequest().body("Invalid credentials format");
        }
        String username = credentials[0];
        String password = credentials[1];

        // Call the login method in the service
        return loginService.login(username, password);
    }

    private String[] parseCredentials(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length());
            String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
            return credentials.split(":", 2);
        }
        return null;
    }
}
