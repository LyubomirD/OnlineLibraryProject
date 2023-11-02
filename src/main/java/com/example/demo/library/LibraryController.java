package com.example.demo.library;

import com.example.demo.exceptions.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/library")
@AllArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping
    public void includeNewBookToLibrary(@RequestBody LibraryRequest request) {
        libraryService.includeNewBook(request);
    }

    @GetMapping("/{title}")
    public List<LibraryRequest> viewBookFromLibrary(@PathVariable String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Not existing title");
        }

        List<LibraryRequest> request = libraryService.viewBook(title);

        return request;
    }
}
