package com.example.online_library.library.user;

import com.example.online_library.library.LibraryRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/library-user")
@AllArgsConstructor
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    @GetMapping
    public List<LibraryRequest> viewAllBooks() {
        return libraryUserService.viewAllBooks();
    }
}
