package com.example.online_library.library.admin;

import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.models.jwt_token.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/library-admin")
@AllArgsConstructor
public class LibraryAdminController {

    private final LibraryAdminService libraryAdminService;
    private final JwtService jwtService;


//    @GetMapping("get-bookId/{title}/{author}")
//    public Long getBookById(@PathVariable String title, @PathVariable String author, HttpServletRequest httpServletRequest) {
//        return libraryAdminService.getBookId(title, author, httpServletRequest);
//    }

    @PostMapping("add-book")
    public ResponseEntity<?> includeNewBookToLibrary(@RequestBody LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        // Get JWT token from request headers
        String token = extractTokenFromRequest(httpServletRequest);
        // Call service method with token
        return libraryAdminService.includeNewBookToLibrary(request, token);
    }

//    @PutMapping("update-book/{book_id}")
//    public void updateBookInformation(@PathVariable Long book_id, @RequestBody LibraryRequestDto request, HttpServletRequest httpServletRequest) {
//        libraryAdminService.changeExistingBookInform(book_id, request, httpServletRequest);
//    }
//
//    @DeleteMapping("delete-book/{book_id}")
//    public void deleteBookFromLibrary(@PathVariable Long book_id, HttpServletRequest httpServletRequest) {
//        libraryAdminService.deleteBookFromLibrary(book_id, httpServletRequest);
//    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        } else {
            throw new RuntimeException("JWT token not found in request headers");
        }
    }
}
