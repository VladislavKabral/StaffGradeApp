package by.kabral.usersservice.util.validator;

import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.model.User;
import by.kabral.usersservice.repository.StatusesRepository;
import by.kabral.usersservice.repository.UsersRepository;
import by.kabral.usersservice.service.PositionsServiceImpl;
import by.kabral.usersservice.service.TeamsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static by.kabral.usersservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class UsersValidator implements Validator<User>{

  private final UsersRepository usersRepository;
  private final StatusesRepository statusesRepository;
  private final TeamsServiceImpl teamsService;
  private final PositionsServiceImpl positionsService;

  @Override
  public void validate(User user) throws EntityValidateException, EntityNotFoundException {
    Optional<User> exitingUser = usersRepository.findByEmail(user.getEmail());
    if ((exitingUser.isPresent()) && (!exitingUser.get().getId().equals(user.getId()))) {
      throw new EntityValidateException(String.format(USER_WITH_GIVEN_EMAIL_ALREADY_EXISTS, user.getEmail()));
    }

    if (user.getManager() != null) {
      UUID managerId = user.getManager().getId();
      if (!usersRepository.existsById(managerId)) {
        throw new EntityNotFoundException(String.format(STATUS_NOT_FOUND, managerId));
      }
    }
    if (user.getTeam() != null) {
      UUID teamId = user.getTeam().getId();
      if (!teamsService.isTeamExist(teamId)) {
        throw new EntityNotFoundException(String.format(TEAM_NOT_FOUND, teamId));
      }
    }
    if (user.getStatus() != null) {
      UUID statusId = user.getStatus().getId();
      if (!statusesRepository.existsById(statusId)) {
        throw new EntityNotFoundException(String.format(STATUS_NOT_FOUND, statusId));
      }
    }
    if (user.getPosition() != null) {
      UUID positionId = user.getPosition().getId();
      if (!positionsService.isPositionExist(positionId)) {
        throw new EntityNotFoundException(String.format(POSITION_NOT_FOUND, positionId));
      }
    }
  }
}
