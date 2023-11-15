package com.example.demo.user_borrows_book;

import com.example.demo.models.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/book-borrow")
@AllArgsConstructor
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    @PostMapping()
    public Optional<AppUser> borrowBooks(@RequestBody BorrowedBookRequest request) {
        return borrowedBookService.addBookToUser(request);
    }

    @PutMapping("/remove")
    public Optional<AppUser> removeBorrowedBook(@RequestBody BorrowedBookRequest request) {
        return borrowedBookService.removeBookFromUser(request);
    }
}
