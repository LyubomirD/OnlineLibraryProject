package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.login.encryptUserSession.EncryptionUtils;
import com.example.online_library.mapper.mappers.LibraryRequestMapper;
import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.hibernate.SessionException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookAdminService bookAdminService;
    private final UserService userService;
    private final LibraryRequestMapper libraryRequestMapper;
    private static final String SESSION_NAME = "MY_SESSION_ID";

    private void checkUserOrThrowException(HttpServletRequest httpServletRequest) {
        System.out.println("HttpServletRequest: " + httpServletRequest.getSession());

        String header = httpServletRequest.getHeader("Cookie");
        System.out.println("Header 1: " + header);

        Cookie[] cookies = httpServletRequest.getCookies();
        System.out.println("Cookies[] 2: " + cookies);

        if (cookies == null) {
            throw new SessionException("Session cookie not found");
        }

        for (Cookie cookie : cookies) {
            if (isSessionCookie(cookie)) {
                handleSessionCookie(cookie);
            }
        }
    }

    private boolean isSessionCookie(Cookie cookie) {
        return SESSION_NAME.equals(cookie.getName());
    }

    private void handleSessionCookie(Cookie cookie) {
        try {
            SecretKey secretKey = EncryptionUtils.generateSecretKey();
            String decryptedSessionData = decryptSessionCookie(cookie, secretKey);

            if (decryptedSessionData.isBlank()) {
                throw new SessionException("Session cookie not found");
            }

            String[] sessionParts = decryptedSessionData.split(":");
            String email = (sessionParts.length > 1) ? sessionParts[1] : null;
            String role = (sessionParts.length > 2) ? sessionParts[2] : null;

            if (!userService.findUserByEmailAndRoleAdmin(email, UserRole.valueOf(role))) {
                throw new AdminAccessDeniedException("User not found, or user is not an administrator");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String decryptSessionCookie(Cookie cookie, SecretKey secretKey) throws Exception {
        String encryptedSessionData = cookie.getValue();
        return EncryptionUtils.decrypt(encryptedSessionData, secretKey);
    }


    public Long getBookId(String title, String author, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        return bookAdminService.findBookId(title, author);
    }

    public void includeNewBookToLibrary(LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
        bookAdminService.addNewBook(book);
    }

    public void changeExistingBookInform(Long book_id, LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
        bookAdminService.updateExistingBook(book_id, book);
    }

    public void deleteBookFromLibrary(Long book_id, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        bookAdminService.deleteBook(book_id);
    }
}
