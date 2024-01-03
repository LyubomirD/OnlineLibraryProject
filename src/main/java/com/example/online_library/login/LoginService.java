package com.example.online_library.login;

import com.example.online_library.login.encryptUserSession.EncryptionUtils;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private static final int SESSION_DURATION_SECONDS = 300; // 5 min
    private static final String SESSION_NAME = "MY_SESSION_ID";
    private static final UserRole ADMIN = UserRole.ADMIN;
    private static final UserRole CLIENT = UserRole.CLIENT;


    private boolean authenticateUser(String username, String password) {
        Optional<AppUser> userOptional = userService.findUserByEmail(username);

        if (userOptional.isEmpty()) {
            log.warn("Authentication failure: User '{}' not found", username);
            return false;
        }

        AppUser appUser = userOptional.get();

        if (!(passwordEncoder.matches(password, appUser.getPassword()))) {
            log.warn("Authentication failure for user '{}': Incorrect password", username);
            return false;
        }

        return true;
    }

    private boolean isUserAdmin(String username) {
        return userService.findUserByEmailAndRoleAdmin(username, ADMIN);
    }

    private String generateRandomSessionId() {
        return UUID.randomUUID().toString();
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

    private Cookie createSessionCookie(String sessionId, String username, boolean isAdmin) {
        UserRole userRole = isAdmin ? ADMIN : CLIENT;

        String sessionData = sessionId + ":" + username + ":" + userRole;

        try {
            SecretKey secretKey = EncryptionUtils.generateSecretKey();
            String encryptedSessionData = EncryptionUtils.encrypt(sessionData, secretKey);
            System.out.println("Encrypted session: " + encryptedSessionData);

            Cookie userSession = new Cookie(SESSION_NAME, encryptedSessionData);
            userSession.setMaxAge(SESSION_DURATION_SECONDS);
            userSession.setHttpOnly(true);
            userSession.setPath("/");
            userSession.setDomain("localhost");

            return userSession;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<?> getUserThatLogsCredentials(String authHeader, HttpServletResponse response) {

        String[] credentials = getCredentials(authHeader);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String password = credentials[1];

            if (authenticateUser(username, password)) {
                String sessionId = generateRandomSessionId();
                boolean isAdmin = isUserAdmin(username);

                Cookie userSession = createSessionCookie(sessionId, username, isAdmin);

                log.info("Setting cookie: {}={}", userSession.getName(), userSession.getValue());
                log.info("Cookie session: {}", userSession);

                response.addCookie(userSession);

                return ResponseEntity.ok(Collections.singletonMap(SESSION_NAME, userSession.getValue()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Invalid credentials\"}");
    }

}
