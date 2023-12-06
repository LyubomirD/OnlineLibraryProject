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
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
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
                String sessionId = generateRandomSessionId();
                boolean isAdmin = loginService.isUserAdmin(username);

                String cookieValue = sessionId + ":" + username + ":" + (isAdmin ? "ADMIN" : "USER");
                Cookie sessionCookie = new Cookie("SESSION_ID", cookieValue);

                sessionCookie.setMaxAge(300); // 5 minutes
                sessionCookie.setHttpOnly(true);
                sessionCookie.setPath("/");

                System.out.println("Setting cookie: " + sessionCookie.getName() + "=" + sessionCookie.getValue());
                System.out.println("Cookie value: " + sessionId);
                System.out.println("Cookie session: " + sessionCookie);
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
