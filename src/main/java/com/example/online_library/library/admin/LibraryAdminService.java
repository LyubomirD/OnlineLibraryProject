package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.login.encryptUserSession.EncryptionUtils;
import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.mapper.mappers.LibraryRequestMapper;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.hibernate.SessionException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookAdminService bookAdminService;
    private final UserService userService;
    private final LibraryRequestMapper libraryRequestMapper;
    private static final String SESSION_NAME = "MY_SESSION_ID";
    private static final UserRole ADMIN = UserRole.ADMIN;


    private void checkSessionForUserExistingAndAdminRole(HttpServletRequest httpServletRequest) throws RuntimeException {
        Cookie[] cookies = httpServletRequest.getCookies();
        System.out.println("Cookies[]: " + cookies);

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

    private void handleSessionCookie(Cookie cookie) throws RuntimeException{
        try {
            SecretKey secretKey = EncryptionUtils.generateSecretKey();
            String decryptedSessionData = decryptSessionCookie(cookie, secretKey);

            System.out.println("Decrypted session: " + decryptedSessionData);

            String[] sessionParts = decryptedSessionData.split(":");
            String email = (sessionParts.length > 1) ? sessionParts[1] : null;
            String role = (sessionParts.length > 2) ? sessionParts[2] : null;

            System.out.println("Email: " + email);
            System.out.println("Role: " + role);

            if (!ADMIN.equals(UserRole.valueOf(role))) {
                throw new AdminAccessDeniedException("User is not an administrator");
            }

            if (!userService.findUserByEmailAndRole(email, ADMIN)) {
                throw new AdminAccessDeniedException("User not found or not an administrator");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error handling session cookie", e);
        }
    }

    private String decryptSessionCookie(Cookie cookie, SecretKey secretKey) throws Exception {
        String encryptedSessionData = cookie.getValue();
        return EncryptionUtils.decrypt(encryptedSessionData, secretKey);
    }


    public Long getBookId(String title, String author, HttpServletRequest httpServletRequest) {

        try {
        checkSessionForUserExistingAndAdminRole(httpServletRequest);

        return bookAdminService.findBookId(title, author);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("User does not have permission to add a book", e);
        }
    }

    public void includeNewBookToLibrary(LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            checkSessionForUserExistingAndAdminRole(httpServletRequest);

            Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
            bookAdminService.addNewBook(book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("User does not have permission to add a book", e);
        }
    }

    public void changeExistingBookInform(Long book_id, LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        try {
            checkSessionForUserExistingAndAdminRole(httpServletRequest);

            Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
            bookAdminService.updateExistingBook(book_id, book);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("User does not have permission to add a book", e);
        }
    }

    public void deleteBookFromLibrary(Long book_id, HttpServletRequest httpServletRequest) {
        try {
            checkSessionForUserExistingAndAdminRole(httpServletRequest);

            bookAdminService.deleteBook(book_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("User does not have permission to add a book", e);
        }
    }
}
