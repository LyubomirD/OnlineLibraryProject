package com.example.online_library.user_borrows_book;

import com.example.online_library.exceptions.EmailValidationException;
import com.example.online_library.login.encryptUserSession.EncryptionUtils;
import com.example.online_library.mapper.mappers.BorrowBookRequestMapper;
import com.example.online_library.mapper.dto.BorrowBookRequestDto;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.hibernate.SessionException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BorrowedBookService {

    private final BookAdminService bookAdminService;
    private final UserService userService;
    private final BorrowBookRequestMapper borrowBookRequestMapper;
    private static final String SESSION_NAME = "MY_SESSION_ID";

    private String getEmailFromUserSession(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies == null) {
            throw new SessionException("Session cookie not found");
        }

        String email = null;
        for (Cookie cookie : cookies) {
            if (isSessionCookie(cookie)) {
                email = handleSessionCookie(cookie);
            }
        }

        return email;
    }

    private boolean isSessionCookie(Cookie cookie) {
        return SESSION_NAME.equals(cookie.getName());
    }

    private String handleSessionCookie(Cookie cookie) {
        try {
            SecretKey secretKey = EncryptionUtils.generateSecretKey();
            String decryptedSessionData = decryptSessionCookie(cookie, secretKey);

            if (decryptedSessionData.isBlank()) {
                throw new SessionException("Session cookie not found");
            }

            String[] sessionParts = decryptedSessionData.split(":");

            return (sessionParts.length > 1) ? sessionParts[1] : null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new UsernameNotFoundException("User with that email does not exist");
    }

    private String decryptSessionCookie(Cookie cookie, SecretKey secretKey) throws Exception {
        String encryptedSessionData = cookie.getValue();
        return EncryptionUtils.decrypt(encryptedSessionData, secretKey);
    }


    public List<BorrowBookRequestDto> viewAllUsersBook(HttpServletRequest httpServletRequest) {
        String email = getEmailFromUserSession(httpServletRequest);

        if (email == null) {
            throw new EmailValidationException("Email is not validated or not registered");
        }

        Optional<AppUser> user = userService.findUserByEmail(email);

        List<Book> userBooks = user.get().getBooks();

        return borrowBookRequestMapper.bookListToBorrowBookRequestDtoList(userBooks);
    }

    public Optional<AppUser> addBookToUser(BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        return modifyBookAction(request, true, httpServletRequest);
    }

    public Optional<AppUser> removeBookFromUser(BorrowBookRequestDto request, HttpServletRequest httpServletRequest) {
        return modifyBookAction(request, false, httpServletRequest);
    }

    private Optional<AppUser> modifyBookAction(BorrowBookRequestDto request, boolean addBook, HttpServletRequest httpServletRequest) {
        String email = getEmailFromUserSession(httpServletRequest);

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

}
