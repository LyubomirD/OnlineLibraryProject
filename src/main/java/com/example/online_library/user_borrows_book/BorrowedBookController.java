package com.example.online_library.user_borrows_book;

import com.example.online_library.mapper.dto.BorrowBookRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book-borrow")
@AllArgsConstructor
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    @GetMapping("/view")
    public List<BorrowBookRequestDto> viewUserBook(HttpServletRequest httpServletRequest) {
        return borrowedBookService.viewAllUsersBook(httpServletRequest);
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBooks(@RequestBody BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            borrowedBookService.addBookToUser(request, httpServletRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to borrow book: " + e.getMessage());
        }
    }

    @PutMapping("/remove")
    public ResponseEntity<?> removeBorrowedBook(@RequestBody BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            borrowedBookService.removeBookFromUser(request, httpServletRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove book: " + e.getMessage());
        }
    }
}
