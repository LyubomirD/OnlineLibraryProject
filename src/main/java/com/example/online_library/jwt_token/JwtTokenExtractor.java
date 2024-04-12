package com.example.online_library.jwt_token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtTokenExtractor {

    public String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        } else {
            throw new RuntimeException("JWT token not found in request headers");
        }
    }
}
