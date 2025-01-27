package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.TeamDto;
import by.kabral.usersservice.dto.TeamsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.mapper.TeamsMapper;
import by.kabral.usersservice.model.Team;
import by.kabral.usersservice.repository.TeamsRepository;
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
  public TeamDto save(TeamDto teamDto) {
    return teamsMapper.toDto(teamsRepository
            .save(teamsMapper.toEntity(teamDto)));
  }

  @Override
  @Transactional
  public TeamDto update(UUID id, TeamDto teamDto) throws EntityNotFoundException {
    if (!teamsRepository.existsById(id)) {
      throw new EntityNotFoundException(TEAM_NOT_FOUND);
    }
    Team team = teamsMapper.toEntity(teamDto);
    team.setId(id);
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
