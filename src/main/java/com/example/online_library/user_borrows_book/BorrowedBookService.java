package com.example.online_library.user_borrows_book;

import com.example.online_library.exceptions.EmailValidationException;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookAdminService bookAdminService;
    private final UserService userService;

    private String getEmailFromCookie(HttpServletRequest httpServletRequest) {
        String customCookieHeader = httpServletRequest.getHeader("Your-Custom-Cookie-Header");
        System.out.println("Custom header: " + customCookieHeader);

        if (customCookieHeader == null || customCookieHeader.isEmpty()) {
            return null;
        }

        String[] cookieParts = customCookieHeader.split(":");
        String email = (cookieParts.length > 1) ? cookieParts[1] : null;
        System.out.println("Cookie parts: " + Arrays.toString(cookieParts));
        System.out.println("Email: " + email);

        return email;
    }


    public List<BorrowedBookRequest> viewAllUsersBook(HttpServletRequest httpServletRequest) {
        String email = getEmailFromCookie(httpServletRequest);

        if (email == null) {
            throw new EmailValidationException("Email is not validated or not registered");
        }

        Optional<AppUser> user = userService.findUserByEmail(email);

        List<Book> userBooks = user.get().getBooks();
        return convertToBorrowedBookResponse(userBooks);
    }

    private List<BorrowedBookRequest> convertToBorrowedBookResponse(List<Book> books) {
        return books.stream()
                .map(book -> new BorrowedBookRequest(
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCoAuthor(),
                        book.getCategories()
                ))
                .collect(Collectors.toList());
    }

    private Optional<AppUser> modifyBookAction(BorrowedBookRequest request, boolean addBook, HttpServletRequest httpServletRequest) {
        String email = getEmailFromCookie(httpServletRequest);
        System.out.println("USER EMAIL: " + email);


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
