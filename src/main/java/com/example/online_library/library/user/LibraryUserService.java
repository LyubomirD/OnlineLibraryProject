package com.example.online_library.library.user;

import com.example.online_library.mapper.appuser.LibraryRequestMapper;
import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LibraryUserService {

    private final BookSearchService bookSearchService;
    private final LibraryRequestMapper libraryRequestMapper;

    public List<LibraryRequestDto> viewAllBooks() {
        List<Book> bookList = bookSearchService.viewAllBooks();
        return libraryRequestMapper.bookListToLibraryRequestDtoList(bookList);
    }
}
