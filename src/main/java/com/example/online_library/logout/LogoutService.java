package com.example.online_library.logout;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogoutService {

    public boolean logoutUser() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
