package com.example.online_library.login;

import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Optional<AppUser> loginUser() {
        String authenticatedUsername = getAuthenticatedUsername();

        return Optional.ofNullable(authenticatedUsername)
                .flatMap(userService::findUserByEmail);
    }
}
