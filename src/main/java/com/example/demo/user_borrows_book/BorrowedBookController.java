package com.example.demo.user_borrows_book;

import com.example.demo.models.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/borrow")
@AllArgsConstructor
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    @PostMapping
    public Optional<AppUser> userBorrowsBooks(@RequestBody BorrowedBookRequest request) {
        return borrowedBookService.addBookToUser(request);
    }
}
