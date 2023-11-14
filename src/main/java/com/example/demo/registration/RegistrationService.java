package com.example.demo.registration;

import com.example.demo.models.appuser.AppUser;
import com.example.demo.models.appuser.UserRole;
import com.example.demo.models.appuser.UserService;
import com.example.demo.models.token.ConfirmationToken;
import com.example.demo.models.token.ConfirmationTokenService;
import com.example.demo.validators.EmailValidator;
import com.example.demo.validators.PasswordValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String registerClient(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidEmail && !isValidPassword) {
            throw new IllegalStateException("Email and password are not valid!");
        }

        String token = userService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );

        return token;
    }

    public String registerAdministrator(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidEmail && !isValidPassword) {
            throw new IllegalStateException("Email and password are not valid!");
        }

        String token = userService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.ADMIN
                )
        );

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }

}
