package com.example.online_library.mapper.appuser;

import com.example.online_library.mapper.dto.AppUserDto;
import com.example.online_library.models.appuser.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto appUserToAppUserDto(AppUser appUser);

    @Mapping(target = "firstName", source = "appUserDto.firstName")
    @Mapping(target = "lastName", source = "appUserDto.lastName")
    @Mapping(target = "email", source = "appUserDto.email")
    @Mapping(target = "password", source = "appUserDto.password")
    AppUser appUserDtoToAppUser(AppUserDto appUserDto);
}
