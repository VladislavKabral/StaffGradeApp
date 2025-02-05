package by.kabral.usersservice.util.validator;

import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.model.User;
import by.kabral.usersservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.usersservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class UsersValidator implements Validator<User>{

  private final UsersRepository usersRepository;

  @Override
  public void validate(User user) throws EntityValidateException {
    Optional<User> exitingUser = usersRepository.findByEmail(user.getEmail());
    if ((exitingUser.isPresent()) && (!exitingUser.get().getId().equals(user.getId()))) {
      throw new EntityValidateException(String.format(USER_WITH_GIVEN_EMAIL_ALREADY_EXISTS, user.getEmail()));
    }

  }
}
