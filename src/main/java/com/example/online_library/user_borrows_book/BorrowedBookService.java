package com.example.online_library.user_borrows_book;

import com.example.online_library.exceptions.EmailValidationException;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookAdminService bookAdminService;
    private final UserService userService;

    public Set<Book> viewAllUsersBook() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new EmailValidationException("Email is not validated of not registrated");
        }
        String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);

        return user.get().getBooks();
    }

    private Optional<AppUser> modifyBookAction(BorrowedBookRequest request, boolean addBook) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();
            Optional<AppUser> userOptional = userService.findUserByEmail(email);

            String title = request.getTitle();
            String author = request.getAuthor();
            Optional<Book> bookOptional = bookAdminService.findBookByTitleAndAuthor(title, author);

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
