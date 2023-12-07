package com.example.online_library.logout;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class LogoutService {

    public boolean logoutUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            SecurityContextHolder.clearContext();

            Cookie sessionCookie = new Cookie("MY_SESSION_ID", null);
            sessionCookie.setMaxAge(0);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");

            httpServletResponse.addCookie(sessionCookie);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
