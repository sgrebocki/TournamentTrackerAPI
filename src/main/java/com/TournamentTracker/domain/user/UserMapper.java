package com.TournamentTracker.domain.user;

import com.TournamentTracker.config.CustomMapperConfig;
import com.TournamentTracker.domain.user.model.AuthUserDto;
import com.TournamentTracker.domain.user.model.User;
import com.TournamentTracker.domain.user.model.UserCreateDto;
import com.TournamentTracker.domain.user.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = CustomMapperConfig.class)
public interface UserMapper {
    @Mapping(source = "team.id", target = "teamId")
    UserDto toDto(User user);
    UserDto toDto(AuthUserDto authUserDto);
    List<UserDto> toDtoList(List<User> userList);
    @Mapping(source = "teamId", target = "team.id")
    User toEntity(UserDto userDto);
    User toEntity(UserCreateDto userCreateDto);
    AuthUserDto toAuthUserDto(User user);
    User toEntity(AuthUserDto authUserDto);
}
