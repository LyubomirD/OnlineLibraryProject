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
// For removing the session
// @PostMapping("/logout")
//    public ResponseEntity<?> logout(HttpServletResponse response) {
//        // Invalidate the session by clearing the session cookie
//        Cookie sessionCookie = new Cookie("SESSION_ID", null);
//        sessionCookie.setMaxAge(0);
//        sessionCookie.setPath("/");
//        response.addCookie(sessionCookie);
//
//        return ResponseEntity.ok("{\"message\":\"Logout successful\"}");
//    }
//
