package com.example.online_library.mapper.mappers;

import com.example.online_library.mapper.dto.BorrowBookRequestDto;
import com.example.online_library.models.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BorrowBookRequestMapper {

    @Mapping(target = "category", source = "book.categories")
    BorrowBookRequestDto bookToBorrowBookRequestDto(Book book);

    @Mapping(target = "category", source = "book.categories")
    List<BorrowBookRequestDto> bookListToBorrowBookRequestDtoList(List<Book> book);

    @Mapping(target = "categories", source = "borrowBookRequestDto.category")
    Book borrowBookRequestDtoToBook(BorrowBookRequestDto borrowBookRequestDto);
}
