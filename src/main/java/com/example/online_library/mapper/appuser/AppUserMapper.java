package com.example.online_library.mapper.appuser;

import com.example.online_library.mapper.dto.AppUserDto;
import com.example.online_library.models.appuser.AppUser;
import com.example.online_library.models.appuser.UserRole;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto appUserToAppUserDto(AppUser appUser);

    AppUser appUserDtoToAppUser(AppUserDto appUserDto);
}
