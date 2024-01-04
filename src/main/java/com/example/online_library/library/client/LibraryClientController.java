package com.example.online_library.library.client;

import com.example.online_library.mapper.dto.LibraryRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/library-user")
@AllArgsConstructor
public class LibraryClientController {

    private final LibraryClientService libraryClientService;

    @GetMapping
    public List<LibraryRequestDto> viewAllBooks() {
        return libraryClientService.viewAllBooks();
    }
}
