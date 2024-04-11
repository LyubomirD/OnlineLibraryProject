package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.mapper.mappers.LibraryRequestMapper;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import com.example.online_library.models.jwt_token.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookAdminService bookAdminService;
    private final UserService userService;
    private final LibraryRequestMapper libraryRequestMapper;
    private final JwtService jwtService;
    private static final UserRole ADMIN = UserRole.ADMIN;

    private void checkUserIsAdmin(String token) {
        String username = jwtService.extractUsername(token);
        if (!userService.findUserByEmailAndRole(username, ADMIN)) {
            throw new AdminAccessDeniedException("User is not an administrator");
        }
    }

//    public Long getBookId(String title, String author, HttpServletRequest httpServletRequest) {
//
//        try {
//        checkSessionForUserExistingAndAdminRole(httpServletRequest);
//
//        return bookAdminService.findBookId(title, author);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("User does not have permission to add a book", e);
//        }
//    }

    public ResponseEntity<?> includeNewBookToLibrary(LibraryRequestDto request, String token) {
        checkUserIsAdmin(token);
        Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
        bookAdminService.addNewBook(book);
        return null;
    }

//
//    public void changeExistingBookInform(Long book_id, LibraryRequestDto request, HttpServletRequest httpServletRequest) {
//        try {
//            checkSessionForUserExistingAndAdminRole(httpServletRequest);
//
//            Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
//            bookAdminService.updateExistingBook(book_id, book);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("User does not have permission to add a book", e);
//        }
//    }
//
//    public void deleteBookFromLibrary(Long book_id, HttpServletRequest httpServletRequest) {
//        try {
//            checkSessionForUserExistingAndAdminRole(httpServletRequest);
//
//            bookAdminService.deleteBook(book_id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("User does not have permission to add a book", e);
//        }
//    }
}
