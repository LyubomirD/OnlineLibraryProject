package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.library.LibraryRequest;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookAdminService bookAdminService;

    private void checkUserOrThrowException(HttpServletRequest httpServletRequest) {
        String customCookieHeader = httpServletRequest.getHeader("Your-Custom-Cookie-Header");

        if (customCookieHeader != null) {
            String[] cookieParts = customCookieHeader.split(":");
            String role = (cookieParts.length > 2) ? cookieParts[2] : null;

            if (!"ADMIN".equals(role)) {
                throw new AdminAccessDeniedException("Access denied, user not administrator");
            }
        } else {
            throw new AdminAccessDeniedException("Access denied, user not administrator");
        }
    }



    public Long getBookId(String title, String author, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

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

    public void changeExistingBookInform(Long book_id, LibraryRequest request, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

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

    public void deleteBookFromLibrary(Long book_id, HttpServletRequest httpServletRequest) {
        checkUserOrThrowException(httpServletRequest);

        bookAdminService.deleteBook(book_id);
    }
}
