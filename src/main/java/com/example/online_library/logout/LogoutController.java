//package com.example.online_library.logout;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/v1/logout")
//@AllArgsConstructor
//public class LogoutController {
//
//    private final LogoutService logoutService;
//
//    @PostMapping

//    public ResponseEntity<Void> logoutUser() {
//        boolean logoutSuccessful = logoutService.logoutUser();
//
//        if (logoutSuccessful) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//}
