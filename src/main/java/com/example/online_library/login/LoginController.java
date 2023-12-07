package com.example.online_library.login;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    private static final int SESSION_DURATION_SECONDS = 300; // 5 minutes

    @PostMapping
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader,
                                   HttpServletResponse response) {
        String[] credentials = getCredentials(authHeader);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String password = credentials[1];

            if (loginService.authenticateUser(username, password)) {
                String sessionId = generateRandomSessionId();
                boolean isAdmin = loginService.isUserAdmin(username);

                String cookieValue = sessionId + ":" + username + ":" + (isAdmin ? "ADMIN" : "USER");
                Cookie sessionCookie = new Cookie("MY_SESSION_ID", cookieValue);

                sessionCookie.setMaxAge(SESSION_DURATION_SECONDS);
                sessionCookie.setHttpOnly(true);
                sessionCookie.setPath("/");

                log.info("Setting cookie: {}={}", sessionCookie.getName(), sessionCookie.getValue());
                log.info("Cookie session: {}", sessionCookie);

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
