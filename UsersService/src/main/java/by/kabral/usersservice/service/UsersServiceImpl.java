package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.NewUserDto;
import by.kabral.usersservice.dto.UserDto;
import by.kabral.usersservice.dto.UsersListDto;
import by.kabral.usersservice.dto.UsersPageDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.mapper.UsersMapper;
import by.kabral.usersservice.model.User;
import by.kabral.usersservice.repository.StatusRepository;
import by.kabral.usersservice.repository.UsersRepository;
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
  private final StatusRepository statusRepository;
  private final UsersMapper usersMapper;


  @Override
  @Transactional(readOnly = true)
  public UsersListDto findAll() {
    return UsersListDto.builder()
            .users(usersMapper.toListDto(usersRepository.findAll()))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public User findEntity(UUID id) throws EntityNotFoundException {
    return usersRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id.toString()));
  }

  public UsersPageDto findUsersPage(int index, int count, String sortField) {
//    if ((index <= 0) || (count <= 0)) {
//      log.info(RECEIVED_PAGE_PARAMETERS_ARE_INVALID);
//      throw new InvalidRequestDataException(INVALID_PAGE_REQUEST);
//    }

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
  public UserDto save(NewUserDto newUserDto) {
    newUserDto.setStatus(statusRepository.findByName(AT_WORK));
    //TODO: encode password
    return usersMapper.toDto(usersRepository
            .save(usersMapper.toEntity(newUserDto)));
  }

  @Override
  @Transactional
  public UserDto update(UUID id, NewUserDto entity) throws EntityNotFoundException {
    if (!usersRepository.existsById(id)) {
      throw new EntityNotFoundException(USER_NOT_FOUND);
    }

    User user = usersMapper.toEntity(entity);
    user.setId(id);
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
