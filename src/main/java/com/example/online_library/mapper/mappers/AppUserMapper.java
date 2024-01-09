package com.example.online_library.mapper.mappers;

import com.example.online_library.mapper.dto.AppUserDto;
import com.example.online_library.models.appuser.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto appUserToAppUserDto(AppUser appUser);

    AppUser appUserDtoToAppUser(AppUserDto appUserDto);
}

