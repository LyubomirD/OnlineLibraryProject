package com.example.online_library.user_borrows_book;

import com.example.online_library.exceptions.EmailValidationException;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookAdminService bookAdminService;
    private final UserService userService;

    private String getEmailFromCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String email = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("MY_SESSION_ID".equals(cookie.getName())) {
                    String[] cookieParts = cookie.getValue().split(":");
                    if (cookieParts.length > 1) {
                        email = cookieParts[1];
                        break;
                    }
                }
            }
        }

        return email;
    }

    public Set<Book> viewAllUsersBook(HttpServletRequest httpServletRequest) {
        String email = getEmailFromCookie(httpServletRequest);

        if (email == null) {
            throw new EmailValidationException("Email is not validated or not registered");
        }

        Optional<AppUser> user = userService.findUserByEmail(email);

        return user.get().getBooks();
    }

    private Optional<AppUser> modifyBookAction(BorrowedBookRequest request, boolean addBook, HttpServletRequest httpServletRequest) {
        String email = getEmailFromCookie(httpServletRequest);

        if (email != null) {
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

    public Optional<AppUser> addBookToUser(BorrowedBookRequest request, HttpServletRequest httpServletRequest) {
        return modifyBookAction(request, true, httpServletRequest);
    }

    public Optional<AppUser> removeBookFromUser(BorrowedBookRequest request, HttpServletRequest httpServletRequest) {
        return modifyBookAction(request, false, httpServletRequest);
    }
}
