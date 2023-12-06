package com.example.online_library.login;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestHeader("Authorization") String authHeader,
                                   HttpServletResponse response) {
        String[] credentials = getCredentials(authHeader);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String password = credentials[1];

            if (loginService.authenticateUser(username, password)) {
                // Generate a random session ID (you may use a more robust method)
                String sessionId = generateRandomSessionId();
                boolean isAdmin = loginService.isUserAdmin(username); // Assuming you have a method to check if the user is an admin

                // Include role information in the cookie value
                String cookieValue = sessionId + ":" + (isAdmin ? "ADMIN" : "USER");

                Cookie sessionCookie = new Cookie("SESSION_ID", cookieValue);
                sessionCookie.setMaxAge(300); // 5 minutes
                sessionCookie.setHttpOnly(true);
                sessionCookie.setPath("/");
                response.addCookie(sessionCookie);
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

    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
    }
}
