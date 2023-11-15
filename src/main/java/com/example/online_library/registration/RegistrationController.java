package com.example.online_library.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/client")
    public String registerClient(@RequestBody RegistrationRequest request) {
        return registrationService.registerClient(request);
    }
    @PostMapping("/administrator")
    public String registerAdministrator(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdministrator(request);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
