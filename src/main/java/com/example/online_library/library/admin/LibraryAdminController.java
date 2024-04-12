package com.example.online_library.library.admin;

import com.example.online_library.mapper.dto.LibraryRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/library-admin")
@AllArgsConstructor
public class LibraryAdminController {

    private final LibraryAdminService libraryAdminService;

    @GetMapping("get-bookId/{title}/{author}")
    public Long getBookById(@PathVariable String title, @PathVariable String author, HttpServletRequest httpServletRequest) {
        return libraryAdminService.getBookId(title, author, httpServletRequest);
    }

    @PostMapping("add-book")
    public ResponseEntity<?> includeNewBookToLibrary(@RequestBody LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            libraryAdminService.includeNewBookToLibrary(request, httpServletRequest);
            return ResponseEntity.ok("Book added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add book: " + e.getMessage());
        }
    }

    @PutMapping("update-book/{book_id}")
    public ResponseEntity<?> updateBookInformation(@PathVariable Long book_id, @RequestBody LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            libraryAdminService.changeExistingBookInform(book_id, request, httpServletRequest);
            return ResponseEntity.ok("Book updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update book: " + e.getMessage());
        }
    }

    @DeleteMapping("delete-book/{book_id}")
    public ResponseEntity<?> deleteBookFromLibrary(@PathVariable Long book_id, HttpServletRequest httpServletRequest) {
        try {
            libraryAdminService.deleteBookFromLibrary(book_id, httpServletRequest);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete book: " + e.getMessage());
        }
    }
}
