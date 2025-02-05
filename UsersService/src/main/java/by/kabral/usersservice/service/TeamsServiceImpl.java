package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.TeamDto;
import by.kabral.usersservice.dto.TeamsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.mapper.TeamsMapper;
import by.kabral.usersservice.model.Team;
import by.kabral.usersservice.repository.TeamsRepository;
import by.kabral.usersservice.util.validator.TeamsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static by.kabral.usersservice.util.Message.*;

@Service
@RequiredArgsConstructor
public class TeamsServiceImpl implements EntitiesService<TeamsListDto, Team, TeamDto, TeamDto>{

  private final TeamsRepository teamsRepository;
  private final TeamsMapper teamsMapper;
  private final TeamsValidator teamsValidator;

  @Override
  @Transactional(readOnly = true)
  public TeamsListDto findAll() {
    return teamsMapper.toListDto(teamsRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public Team findEntity(UUID id) throws EntityNotFoundException {
    return teamsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
  }

  @Override
  @Transactional(readOnly = true)
  public TeamDto findById(UUID id) throws EntityNotFoundException {
    return teamsMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  public TeamDto save(TeamDto teamDto) throws EntityValidateException {
    Team team = teamsMapper.toEntity(teamDto);
    teamsValidator.validate(team);
    return teamsMapper.toDto(teamsRepository.save(team));
  }

  @Override
  @Transactional
  public TeamDto update(UUID id, TeamDto teamDto) throws EntityNotFoundException, EntityValidateException {
    if (!teamsRepository.existsById(id)) {
      throw new EntityNotFoundException(TEAM_NOT_FOUND);
    }

    Team team = teamsMapper.toEntity(teamDto);
    team.setId(id);
    teamsValidator.validate(team);
    return teamsMapper.toDto(teamsRepository.save(team));
  }

  @Override
  @Transactional
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!teamsRepository.existsById(id)) {
      throw new EntityNotFoundException(TEAM_NOT_FOUND);
    }

    teamsRepository.deleteById(id);
    return id;
  }
}
