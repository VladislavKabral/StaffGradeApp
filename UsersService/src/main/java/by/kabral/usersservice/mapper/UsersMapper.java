package by.kabral.usersservice.mapper;

import by.kabral.usersservice.dto.NewUserDto;
import by.kabral.usersservice.dto.UserDto;
import by.kabral.usersservice.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersMapper {

  private final ModelMapper modelMapper;

  public UserDto toDto(User user) {
    return modelMapper.map(user, UserDto.class);
  }

  public User toEntity(NewUserDto newUserDto) {
    return modelMapper.map(newUserDto, User.class);
  }

  public List<UserDto> toListDto(List<User> users) {
    return users.stream().map(this::toDto).toList();
  }
}
