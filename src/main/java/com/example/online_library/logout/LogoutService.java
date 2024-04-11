
package com.example.online_library.logout;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


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
