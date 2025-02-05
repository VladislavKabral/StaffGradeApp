package by.kabral.usersservice.controller;

import by.kabral.usersservice.dto.NewUserDto;
import by.kabral.usersservice.dto.UserDto;
import by.kabral.usersservice.dto.UsersListDto;
import by.kabral.usersservice.dto.UsersPageDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.exception.InvalidRequestDataException;
import by.kabral.usersservice.service.UsersServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersServiceImpl usersService;

  @GetMapping
  public ResponseEntity<UsersListDto> getUsers() {
    return new ResponseEntity<>(usersService.findAll(), HttpStatus.OK);
  }

  @GetMapping(params = {"status"})
  public ResponseEntity<UsersListDto> getUsersByStatus(@RequestParam(value = "status") String status) {
    return new ResponseEntity<>(usersService.findUsersByStatus(status), HttpStatus.OK);
  }

  @GetMapping(params = {"page", "size", "sort"})
  public ResponseEntity<UsersPageDto> getUsersPage(@RequestParam(name = "page", required = false, defaultValue = "1")  int page,
                                                   @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "lastname") String sortField) throws InvalidRequestDataException {
    return new ResponseEntity<>(usersService.findUsersPage(page, size, sortField), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(usersService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserDto> saveUser(@RequestBody @Valid NewUserDto newUserDto) throws EntityValidateException {
    return new ResponseEntity<>(usersService.save(newUserDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody @Valid NewUserDto newUserDto) throws EntityNotFoundException, EntityValidateException {
    return new ResponseEntity<>(usersService.update(id, newUserDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deleteUser(@PathVariable UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(usersService.delete(id), HttpStatus.OK);
  }
}
