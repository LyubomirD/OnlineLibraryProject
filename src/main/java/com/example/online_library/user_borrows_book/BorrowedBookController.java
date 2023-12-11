package com.example.online_library.user_borrows_book;

import com.example.online_library.models.appuser.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/book-borrow")
@AllArgsConstructor
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    @GetMapping("/view")
    public List<BorrowedBookRequest> viewUserBook(HttpServletRequest httpServletRequest) {
        return borrowedBookService.viewAllUsersBook(httpServletRequest);
    }

    @PostMapping("/borrow")
    public Optional<AppUser> borrowBooks(@RequestBody BorrowedBookRequest request, HttpServletRequest httpServletRequest) {
        return borrowedBookService.addBookToUser(request, httpServletRequest);
    }

    @PutMapping("/remove")
    public Optional<AppUser> removeBorrowedBook(@RequestBody BorrowedBookRequest request, HttpServletRequest httpServletRequest) {
        return borrowedBookService.removeBookFromUser(request, httpServletRequest);
    }
}
