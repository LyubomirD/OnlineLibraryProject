package com.example.demo.library;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LibraryRequest {
    private final String title;
    private final String author;
    private final String coAuthor;
}
