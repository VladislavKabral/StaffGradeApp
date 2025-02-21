package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.NewUserDto;
import by.kabral.usersservice.dto.UserDto;
import by.kabral.usersservice.dto.UsersListDto;
import by.kabral.usersservice.dto.UsersPageDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.exception.InvalidRequestDataException;
import by.kabral.usersservice.mapper.UsersMapper;
import by.kabral.usersservice.model.User;
import by.kabral.usersservice.repository.StatusesRepository;
import by.kabral.usersservice.repository.UsersRepository;
import by.kabral.usersservice.util.validator.UsersValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static by.kabral.usersservice.util.Message.*;
import static by.kabral.usersservice.util.StatusName.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements EntitiesService<UsersListDto, User, UserDto, NewUserDto> {

  private final UsersRepository usersRepository;
  private final StatusesRepository statusRepository;
  private final UsersMapper usersMapper;
  private final UsersValidator usersValidator;

  @Override
  @Transactional(readOnly = true)
  public UsersListDto findAll() {
    return UsersListDto.builder()
            .users(usersMapper.toListDto(usersRepository.findAll()))
            .build();
  }

  @Transactional(readOnly = true)
  public UsersListDto findUsersByStatus(String status) {
    List<User> users;

    if (status.equals(NOT_FIRED)) {
      users = usersRepository.findAll()
              .stream()
              .filter(user -> !user.getStatus().getName().equals(FIRED))
              .toList();
    } else {
      users = usersRepository.findAll()
              .stream()
              .filter(user -> user.getStatus().getName().equals(status))
              .toList();
    }

    return UsersListDto.builder()
            .users(usersMapper.toListDto(users))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public User findEntity(UUID id) throws EntityNotFoundException {
    return usersRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND, id)));
  }

  public UsersPageDto findUsersPage(int index, int count, String sortField) throws InvalidRequestDataException {
    if ((index <= 0) || (count <= 0)) {
      throw new InvalidRequestDataException(INVALID_PAGE_REQUEST);
    }

    List<User> users = usersRepository
            .findAll(PageRequest.of(index - 1, count, Sort.by(sortField))).getContent()
            .stream()
            .filter(user -> !user.getStatus().getName().equals(FIRED))
            .toList();

    return UsersPageDto.builder()
            .page(index)
            .size(count)
            .content(usersMapper.toListDto(users))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto findById(UUID id) throws EntityNotFoundException {
    return usersMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  public UserDto save(NewUserDto newUserDto) throws EntityValidateException {
    User user = usersMapper.toEntity(newUserDto);
    usersValidator.validate(user);
    user.setStatus(statusRepository.findByName(AT_WORK));

    UserDto userDto = usersMapper.toDto(usersRepository.save(user));

    if (userDto.getId() == null) {
      throw new EntityValidateException(String.format(USER_NOT_CREATED, newUserDto.getEmail()));
    }

    return userDto;
  }

  @Override
  @Transactional
  public UserDto update(UUID id, NewUserDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!usersRepository.existsById(id)) {
      throw new EntityNotFoundException(USER_NOT_FOUND);
    }

    User user = usersMapper.toEntity(entity);
    usersValidator.validate(user);
    return usersMapper.toDto(usersRepository.save(user));
  }

  @Override
  @Transactional
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!usersRepository.existsById(id)) {
      throw new EntityNotFoundException(USER_NOT_FOUND);
    }

    usersRepository.deleteById(id);
    return id;
  }
}
