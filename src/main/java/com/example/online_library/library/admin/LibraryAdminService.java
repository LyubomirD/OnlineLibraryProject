package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.library.LibraryRequest;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    //TODO uncomment the checkUserOrThrowException when frontend registration is complete
    private final BookAdminService bookAdminService;

//    private boolean isAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch("ADMIN"::equals);
//    }
//
//    private void checkUserOrThrowException() {
//        if (!isAdmin()) {
//            throw new AdminAccessDeniedException("Access denied, user not administrator");
//        }
//    }


    private void checkUserOrThrowException(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String role = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    String[] cookieParts = cookie.getValue().split(":");
                    if (cookieParts.length > 2) {
                        role = cookieParts[2];
                        break;
                    }
                }
            }
        }

        if (!"ADMIN".equals(role)) {
            throw new AdminAccessDeniedException("Access denied, user not administrator");
        }
    }


    public Long getBookId(String title, String author) {
        //checkUserOrThrowException();

        return bookAdminService.findBookId(title, author);
    }

    public void includeNewBookToLibrary(LibraryRequest request, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        bookAdminService.addNewBook(
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor(),
                        request.getCategory()
                )
        );
    }

    public void changeExistingBookInform(Long book_id, LibraryRequest request) {
        //checkUserOrThrowException();

        bookAdminService.updateExistingBook(
                book_id,
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor(),
                        request.getCategory()
                )
        );
    }

    public void deleteBookFromLibrary(Long book_id) {
        //checkUserOrThrowException();

        bookAdminService.deleteBook(book_id);
    }
}
