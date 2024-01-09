package com.example.online_library.registration;

import com.example.online_library.mapper.dto.AppUserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/client")
    public String registerClient(@RequestBody AppUserDto request) {
        return registrationService.registerClient(request);
    }
    @PostMapping("/administrator")
    public void registerAdministrator(@RequestBody AppUserDto request) {
         registrationService.registerAdministrator(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
