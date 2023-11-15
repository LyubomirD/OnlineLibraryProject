package com.example.online_library.library.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/library-admin")
@AllArgsConstructor
public class LibraryAdminController {

    private final LibraryAdminService libraryAdminService;

    @PostMapping("add-book")
    public void includeNewBookToLibrary(@RequestBody LibraryAdminRequest request) {
        libraryAdminService.includeNewBookToLibrary(request);
    }

    @PutMapping("update-book/{book_id}")
    public void updateBookInformation(@PathVariable Long book_id, @RequestBody LibraryAdminRequest request) {
        libraryAdminService.changeExistingBookInform(book_id, request);

    }

    @DeleteMapping("delete-book/{book_id}")
    public void deleteBookFromLibrary(@PathVariable Long book_id) {
        libraryAdminService.deleteBookFromLibrary(book_id);
    }

}
