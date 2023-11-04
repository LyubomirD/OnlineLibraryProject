package com.example.demo.user_borrows_book;

import com.example.demo.models.appuser.AppUser;
import com.example.demo.models.appuser.UserRepository;
import com.example.demo.models.appuser.UserService;
import com.example.demo.models.book.Book;
import com.example.demo.models.book.BookRepository;
import com.example.demo.models.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookService bookService;
    private final UserService userService;

    public Optional<AppUser> addBookToUser(BorrowedBookRequest borrowedBookRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();
            Optional<AppUser> userOptional = userService.findUserByEmail(email);

            String title = borrowedBookRequest.getTitle();
            String author = borrowedBookRequest.getAuthor();
            Optional<Book> bookOptional = bookService.findBookByTitleAndAuthor(title, author);

            if (userOptional.isPresent() && bookOptional.isPresent()) {
                AppUser user = userOptional.get();
                Book book = bookOptional.get();

                user.getBooks().add(book);

                userService.save(user);

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

}
