package com.example.demo.models.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoryGenre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    MYSTERY("Mystery"),
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    THRILLER("Thriller"),
    DETECTIVE("Detective"),
    HISTORICAL_FICTION("Historical Fiction");
    private String displayName;
}
