package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.TeamDto;
import by.kabral.usersservice.dto.TeamsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.exception.EntityValidateException;
import by.kabral.usersservice.mapper.TeamsMapper;
import by.kabral.usersservice.model.Team;
import by.kabral.usersservice.repository.TeamsRepository;
import by.kabral.usersservice.util.validator.TeamsValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static by.kabral.usersservice.util.CircuitBreakerName.*;
import static by.kabral.usersservice.util.Message.*;
import static by.kabral.usersservice.util.RetryName.*;

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
            .orElseThrow(() -> new EntityNotFoundException(String.format(TEAM_NOT_FOUND, id)));
  }

  @Override
  @Transactional(readOnly = true)
  public TeamDto findById(UUID id) throws EntityNotFoundException {
    return teamsMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_TEAM_BREAKER)
  @Retry(name = SAVE_TEAM_RETRY)
  public TeamDto save(TeamDto entity) throws EntityValidateException {
    Team team = teamsMapper.toEntity(entity);
    teamsValidator.validate(team);

    TeamDto teamDto = teamsMapper.toDto(teamsRepository.save(team));

    if (teamDto.getId() == null) {
      throw new EntityValidateException(String.format(TEAM_NOT_CREATED, team.getName()));
    }

    return teamDto;
  }

  @Override
  @Transactional
  @CircuitBreaker(name = UPDATE_TEAM_BREAKER)
  @Retry(name = UPDATE_TEAM_RETRY)
  public TeamDto update(UUID id, TeamDto teamDto) throws EntityNotFoundException, EntityValidateException {
    if (!teamsRepository.existsById(id)) {
      throw new EntityNotFoundException(TEAM_NOT_FOUND);
    }

    Team team = teamsMapper.toEntity(teamDto);
    teamsValidator.validate(team);
    return teamsMapper.toDto(teamsRepository.save(team));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = DELETE_TEAM_BREAKER)
  @Retry(name = DELETE_TEAM_RETRY)
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!teamsRepository.existsById(id)) {
      throw new EntityNotFoundException(TEAM_NOT_FOUND);
    }

    teamsRepository.deleteById(id);
    return id;
  }
}
