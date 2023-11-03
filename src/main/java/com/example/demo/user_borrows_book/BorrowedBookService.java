package com.example.demo.user_borrows_book;

import com.example.demo.models.appuser.AppUser;
import com.example.demo.models.appuser.UserRepository;
import com.example.demo.models.appuser.UserService;
import com.example.demo.models.book.Book;
import com.example.demo.models.book.BookRepository;
import com.example.demo.models.book.BookService;
import lombok.AllArgsConstructor;
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
        String email = borrowedBookRequest.getEmail();
        Optional<AppUser> userOptional = userService.findUserByEmail(email);

        String title = borrowedBookRequest.getTitle();
        String author = borrowedBookRequest.getAuthor();
        Optional<Book> bookOptional = bookService.findBookByTitleAndAuthor(title, author);

        System.out.println("IF");

        System.out.println("UserOptional: \n" + userOptional);
        System.out.println("BookOptional: \n" +bookOptional);
        if (userOptional.isPresent() && bookOptional.isPresent()) {

            System.out.println("INSIDE IF");

            AppUser user = userOptional.get();
            Book book = bookOptional.get();

            user.getBooks().add(book);

            userService.save(user);

            System.out.println("I MUST HAVE SAVED");
            return Optional.of(user);
        }
        System.out.println("FAIL");
        return Optional.empty();
    }

}
