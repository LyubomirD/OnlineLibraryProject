package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.jwt_token.JwtTokenExtractor;
import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.mapper.mappers.LibraryRequestMapper;
import com.example.online_library.models.appuser.UserRole;
import com.example.online_library.models.appuser.UserService;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import com.example.online_library.jwt_token.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookAdminService bookAdminService;
    private final UserService userService;
    private final LibraryRequestMapper libraryRequestMapper;
    private final JwtService jwtService;
    private final JwtTokenExtractor jwtTokenExtractor;
    private static final UserRole ADMIN = UserRole.ADMIN;


    private void isUserValidAndAdmin(HttpServletRequest httpServletRequest) {
        String token = jwtTokenExtractor.extractTokenFromRequest(httpServletRequest);
        String username = jwtService.extractUsername(token);
        if (!userService.findUserByEmailAndRole(username, ADMIN)) {
            throw new AdminAccessDeniedException("User is not an administrator");
        }
    }


    public Long getBookId(String title, String author, HttpServletRequest httpServletRequest) {
        isUserValidAndAdmin(httpServletRequest);
        return bookAdminService.findBookId(title, author);
    }

    public void includeNewBookToLibrary(LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        isUserValidAndAdmin(httpServletRequest);
        Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
        bookAdminService.addNewBook(book);
    }

    public void changeExistingBookInform(Long book_id, LibraryRequestDto request, HttpServletRequest httpServletRequest) {
        isUserValidAndAdmin(httpServletRequest);
        Book book = libraryRequestMapper.libraryRequestDtoToBook(request);
        bookAdminService.updateExistingBook(book_id, book);
    }

    public void deleteBookFromLibrary(Long book_id, HttpServletRequest httpServletRequest) {
        isUserValidAndAdmin(httpServletRequest);
        bookAdminService.deleteBook(book_id);
    }
}
