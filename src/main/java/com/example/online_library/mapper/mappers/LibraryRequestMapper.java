package com.example.online_library.mapper.mappers;

import com.example.online_library.mapper.dto.LibraryRequestDto;
import com.example.online_library.models.book.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryRequestMapper {
    @Mapping(target = "category", source = "book.categories")
    LibraryRequestDto bookToLibraryRequestDto(Book book);

    @Mapping(target = "category", source = "book.categories")
    List<LibraryRequestDto> bookListToLibraryRequestDtoList(List<Book> book);

    @Mapping(target = "categories", source = "libraryRequestDto.category")
    Book libraryRequestDtoToBook(LibraryRequestDto libraryRequestDto);


}
