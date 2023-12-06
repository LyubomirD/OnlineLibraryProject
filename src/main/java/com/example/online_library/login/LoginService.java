package com.example.online_library.login;

import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean authenticateUser(String username, String password) {
        Optional<AppUser> userOptional = userService.findUserByEmail(username);

        if (userOptional.isPresent()) {
            AppUser appUser = userOptional.get();

            return passwordEncoder.matches(password, appUser.getPassword());
        }

        return false;
    }

    public boolean isUserAdmin(String username) {
        return userService.findUserByEmailAndRoleAdmin(username);
    }
}
