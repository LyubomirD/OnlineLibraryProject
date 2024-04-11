package com.example.online_library.models.appuser;

import com.example.online_library.exceptions.EmailTakenException;
import com.example.online_library.models.uuid_token.ConfirmationToken;
import com.example.online_library.models.uuid_token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        Optional<AppUser> existingUser = userRepository.findByEmail(appUser.getEmail());

        if (existingUser.isPresent()) {
            if (existingUser.get().isEnabled()) {
                throw new EmailTakenException("email already taken");
            } else {
                Long userId = existingUser.get().getId();
                confirmationTokenService.deleteUnConfirmedToken(userId);
                userRepository.deleteById(userId);
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public Optional<AppUser> findUserByEmail(String email) {
        System.out.println("Email:" + userRepository.findByEmail(email));
        return userRepository.findByEmail(email);
    }

    public void save(AppUser user) {
        userRepository.save(user);
    }

    public boolean findUserByEmailAndRole(String email, UserRole role) {
        return userRepository.existsByEmailAndUserRole(email, role);
    }

}
