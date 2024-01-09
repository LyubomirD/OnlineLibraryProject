package com.example.online_library.user_borrows_book;

import com.example.online_library.mapper.dto.BorrowBookRequestDto;
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

    //TODO Change the optionals to DTOs

    @GetMapping("/view")
    public List<BorrowBookRequestDto> viewUserBook(HttpServletRequest httpServletRequest) {
        List<BorrowBookRequestDto> dto = borrowedBookService.viewAllUsersBook(httpServletRequest);
        System.out.println("Books list: " + dto);
        return dto;
    }

    @PostMapping("/borrow")
    public Optional<AppUser> borrowBooks(@RequestBody BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        return borrowedBookService.addBookToUser(request, httpServletRequest);
    }

    @PutMapping("/remove")
    public Optional<AppUser> removeBorrowedBook(@RequestBody BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        return borrowedBookService.removeBookFromUser(request, httpServletRequest);
    }
}
