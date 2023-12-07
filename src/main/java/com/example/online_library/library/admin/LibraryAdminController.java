package com.example.online_library.library.admin;

import com.example.online_library.library.LibraryRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public void includeNewBookToLibrary(@RequestBody LibraryRequest request, HttpServletRequest httpServletRequest) {
        libraryAdminService.includeNewBookToLibrary(request, httpServletRequest);
    }

    @PutMapping("update-book/{book_id}")
    public void updateBookInformation(@PathVariable Long book_id, @RequestBody LibraryRequest request, HttpServletRequest httpServletRequest) {
        libraryAdminService.changeExistingBookInform(book_id, request, httpServletRequest);
    }

    @DeleteMapping("delete-book/{book_id}")
    public void deleteBookFromLibrary(@PathVariable Long book_id, HttpServletRequest httpServletRequest) {
        libraryAdminService.deleteBookFromLibrary(book_id, httpServletRequest);
    }
}
