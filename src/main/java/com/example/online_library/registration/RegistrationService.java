package com.example.online_library.registration;

import com.example.online_library.mail_uuid_token.EmailBuilder;
import com.example.online_library.mail_uuid_token.EmailSender;
import com.example.online_library.mapper.dto.AppUserRequestDto;
import com.example.online_library.mapper.mappers.AppUserMapper;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.uuid_token.ConfirmationToken;
import com.example.online_library.models.uuid_token.ConfirmationTokenService;
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
    private final EmailSender emailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final AppUserMapper appUserMapper;
    private static final UserRole ADMIN = UserRole.ADMIN;
    private static final UserRole CLIENT = UserRole.CLIENT;
    private final EmailBuilder emailBuilder;


    private void validateEmailAndPassword(AppUserRequestDto request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidPassword = passwordValidator.test(request.getPassword());

        if (!isValidEmail && !isValidPassword) {
            throw new IllegalStateException("Email and password are not valid!");
        }
    }

    public String registerClient(AppUserRequestDto request) {
        validateEmailAndPassword(request);

        AppUser appUser = appUserMapper.appUserDtoToAppUser(request);
        appUser.setUserRole(CLIENT);
        String token = userService.signUpUser(appUser);

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), emailBuilder.buildEmail(request.getFirstName(), link));

        return token;
    }


    public String registerAdministrator(AppUserRequestDto request) {
        validateEmailAndPassword(request);

        AppUser appUser = appUserMapper.appUserDtoToAppUser(request);
        appUser.setUserRole(ADMIN);
        String token = userService.signUpUser(appUser);

        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), emailBuilder.buildEmail(request.getFirstName(), link));

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
