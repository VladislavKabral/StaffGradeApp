package by.kabral.usersservice.util.validator;

import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.model.Position;
import by.kabral.usersservice.repository.PositionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.usersservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class PositionsValidator implements Validator<Position> {

  private final PositionsRepository positionsRepository;

  @Override
  public void validate(Position position) throws EntityValidateException {
    Optional<Position> exitingPosition = positionsRepository.findByName(position.getName());
    if ((exitingPosition.isPresent()) && (!exitingPosition.get().getId().equals(position.getId()))) {
      throw new EntityValidateException(String.format(POSITION_WITH_GIVEN_NAME_ALREADY_EXISTS, position.getName()));
    }

  }
}
