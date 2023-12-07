package com.example.online_library.login;

import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean authenticateUser(String username, String password) {
        Optional<AppUser> userOptional = userService.findUserByEmail(username);

        if (userOptional.isEmpty()) {
            log.warn("Authentication failure: User '{}' not found", username);
            return false;
        }

        AppUser appUser = userOptional.get();

        if (!(passwordEncoder.matches(password, appUser.getPassword()))) {
            log.warn("Authentication failure for user '{}': Incorrect password", username);
            return false;
        }

        return true;
    }

    public boolean isUserAdmin(String username) {
        return userService.findUserByEmailAndRoleAdmin(username, UserRole.ADMIN);
    }
}
