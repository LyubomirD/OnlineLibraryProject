package com.example.online_library.login;

import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.jwt_token.JwtService;
import com.example.online_library.web_security.PasswordEncoderConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public ResponseEntity<?> login(String username, String password) {
        // Authenticate user
        UserDetails userDetails = userService.loadUserByUsername(username);
        if (userDetails != null && passwordEncoderConfig.bCryptPasswordEncoder().matches(password, userDetails.getPassword())) {
            // Generate JWT token
            String token = jwtService.generateToken(username);
            // Return token in response
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }
}
