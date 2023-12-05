package com.example.online_library.login;

import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final UserService userService;

    public void loginUser(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<AppUser> authenticatedUser = userService.findUserByEmailAndPassword(email, password);

        if (authenticatedUser.isPresent()) {
            System.out.println("User authenticated successfully");
        } else {
            System.out.println("Authentication failed");
        }
    }
}
