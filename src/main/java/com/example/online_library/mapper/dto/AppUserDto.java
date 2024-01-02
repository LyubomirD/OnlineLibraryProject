package com.example.online_library.mapper.dto;

import lombok.*;

@Data
public class AppUserDto {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
