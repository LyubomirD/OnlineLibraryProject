package com.example.online_library.logout;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class LogoutService {

    private static final String SESSION_NAME = "MY_SESSION_ID";

    public void logoutUser(HttpServletRequest httpServletRequest) {
        try {
            SecurityContextHolder.clearContext();

            Cookie[] cookies = httpServletRequest.getCookies();

            for (Cookie cookie: cookies) {
                if (isSessionCookie(cookie)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSessionCookie(Cookie cookie) {
        return SESSION_NAME.equals(cookie.getName());
    }

}
