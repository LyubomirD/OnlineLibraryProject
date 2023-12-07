package com.example.online_library.logout;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/logout")
@AllArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        boolean logoutSuccessful = logoutService.logoutUser(httpServletRequest, httpServletResponse);

        if (logoutSuccessful) {
            return ResponseEntity.ok("{\"message\":\"Logout successful\"}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Something went wrong\"}");
        }
    }
}
