package com.example.demo.user_borrows_book;

import com.example.demo.models.appuser.AppUser;
import com.example.demo.models.appuser.UserService;
import com.example.demo.models.book.Book;
import com.example.demo.models.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookService bookService;
    private final UserService userService;

    private Optional<AppUser> modifyBookAction(BorrowedBookRequest request, boolean addBook) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();
            Optional<AppUser> userOptional = userService.findUserByEmail(email);

            String title = request.getTitle();
            String author = request.getAuthor();
            Optional<Book> bookOptional = bookService.findBookByTitleAndAuthor(title, author);

            if (userOptional.isPresent() && bookOptional.isPresent()) {
                AppUser user = userOptional.get();
                Book book = bookOptional.get();

                if (addBook) {
                    user.getBooks().add(book);
                } else {
                    user.getBooks().remove(book);
                }

                userService.save(user);

                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    public Optional<AppUser> addBookToUser(BorrowedBookRequest request) {
        return modifyBookAction(request, true);
    }

    public Optional<AppUser> removeBookFromUser(BorrowedBookRequest request) {
        return modifyBookAction(request, false);
    }


}
