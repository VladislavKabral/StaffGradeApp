package by.kabral.usersservice.util.validator;

import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.model.Team;
import by.kabral.usersservice.repository.TeamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.usersservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class TeamsValidator implements Validator<Team> {

  private final TeamsRepository teamsRepository;

  @Override
  public void validate(Team team) throws EntityValidateException {
    Optional<Team> existingTeam = teamsRepository.findByName(team.getName());
    if ((existingTeam.isPresent()) && (!existingTeam.get().getId().equals(team.getId()))) {
      throw new EntityValidateException(String.format(TEAM_WITH_GIVEN_NAME_ALREADY_EXISTS, team.getName()));
    }

  }
}
