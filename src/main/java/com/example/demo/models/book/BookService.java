package com.example.demo.models.book;

import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.models.categories.Category;
import com.example.demo.models.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public void addNewBook(Book book) {
        boolean bookExisting = bookRepository
                .findByTitleAndAuthor(book.getTitle(), book.getAuthor())
                .isPresent();

        if (bookExisting) {
            throw new RuntimeException("Book exists");
        }
        
        Set<Category> categories = new HashSet<>();

        for (Category category : book.getCategories()) {
            Category orCreateCategory = categoryService.findOrCreateCategory(category);
            categories.add(orCreateCategory);
        }

        book.setCategories(categories);

        bookRepository.save(book);
    }


    public List<Book> viewAllSearchBooks(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);

        if (bookList.isEmpty()) {

            throw new BookNotFoundException("Book with that title does not exists");
        }

        return bookList;
    }

    public Optional<Book> findBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    public List<Book> viewAllBooks() {
        return bookRepository.findAll();
    }
}
