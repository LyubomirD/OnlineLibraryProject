package com.example.online_library.mapper.mappers;

import com.example.online_library.mapper.dto.AppUserRequestDto;
import com.example.online_library.models.appuser.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserRequestDto appUserToAppUserDto(AppUser appUser);

    AppUser appUserDtoToAppUser(AppUserRequestDto appUserRequestDto);
}

