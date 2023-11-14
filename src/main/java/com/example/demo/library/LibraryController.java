package com.example.demo.library;

import com.example.demo.exceptions.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/library")
@AllArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public List<LibraryRequest> viewAllBooks() {
        return libraryService.viewAllBooks();
    }

    @PostMapping("add-book")
    public void includeNewBookToLibrary(@RequestBody LibraryRequest request) {
        libraryService.includeNewBookToLibrary(request);
    }

    @GetMapping("search/{title}")
    public List<LibraryRequest> viewAllBookByTitleFromLibrary(@PathVariable String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Not existing title");
        }

        return libraryService.viewAllBookByTitle(title);
    }
}
