
package com.example.online_library.logout;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LogoutService {

    public void logoutUser() {
        SecurityContextHolder.clearContext();
    }


}
