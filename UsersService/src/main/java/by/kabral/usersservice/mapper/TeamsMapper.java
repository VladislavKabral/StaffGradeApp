package by.kabral.usersservice.mapper;

import by.kabral.usersservice.dto.TeamDto;
import by.kabral.usersservice.dto.TeamsListDto;
import by.kabral.usersservice.model.Team;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamsMapper {

  private final ModelMapper modelMapper;

  public TeamDto toDto(Team team) {
    return modelMapper.map(team, TeamDto.class);
  }

  public Team toEntity(TeamDto teamDto) {
    return modelMapper.map(teamDto, Team.class);
  }

  public TeamsListDto toListDto(List<Team> teams) {
    return TeamsListDto.builder()
            .teams(teams.stream().map(this::toDto).toList())
            .build();
  }
}
