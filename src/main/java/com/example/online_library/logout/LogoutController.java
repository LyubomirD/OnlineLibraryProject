package com.example.online_library.logout;

import lombok.AllArgsConstructor;
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
    public void logoutUser(HttpServletRequest httpServletRequest) {
        logoutService.logoutUser(httpServletRequest);
    }
}
