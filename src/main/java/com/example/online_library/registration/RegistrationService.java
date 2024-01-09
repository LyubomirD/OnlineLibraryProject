package com.example.online_library.registration;

import com.example.online_library.mapper.mappers.AppUserMapper;
import com.example.online_library.mapper.dto.AppUserDto;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.token.ConfirmationToken;
import com.example.online_library.models.token.ConfirmationTokenService;
import com.example.online_library.validators.EmailValidator;
import com.example.online_library.validators.PasswordValidator;
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
    private final AppUserMapper appUserMapper;
    private static final UserRole ADMIN = UserRole.ADMIN;
    private static final UserRole CLIENT = UserRole.CLIENT;


    private void validateEmailAndPassword(AppUserDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidEmail && !isValidPassword) {
            throw new IllegalStateException("Email and password are not valid!");
        }
    }

    public void registerClient(AppUserDto request) {
        validateEmailAndPassword(request);

        AppUser appUser = appUserMapper.appUserDtoToAppUser(request);
        appUser.setUserRole(CLIENT);
        String token = userService.signUpUser(appUser);
    }


    public void registerAdministrator(AppUserDto request) {
        validateEmailAndPassword(request);

        AppUser appUser = appUserMapper.appUserDtoToAppUser(request);
        appUser.setUserRole(ADMIN);
        String token = userService.signUpUser(appUser);
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
