package com.example.online_library.mapper.dto;

import lombok.Data;

@Data
public class AppUserRequestDto {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
