package by.kabral.usersservice.controller;

import by.kabral.usersservice.dto.TeamDto;
import by.kabral.usersservice.dto.TeamsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.service.TeamsServiceImpl;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamsController {

  private final TeamsServiceImpl teamsService;

  @GetMapping
  public ResponseEntity<TeamsListDto> getAllTeams() {
    return new ResponseEntity<>(teamsService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TeamDto> getTeamById(@PathVariable UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(teamsService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TeamDto> saveTeam(@RequestBody @Valid TeamDto teamDto) {
    return new ResponseEntity<>(teamsService.save(teamDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TeamDto> updateTeam(@PathVariable UUID id,
                                            @RequestBody @Valid TeamDto teamDto) throws EntityNotFoundException {
    return new ResponseEntity<>(teamsService.update(id, teamDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deleteTeam(@PathVariable UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(teamsService.delete(id), HttpStatus.OK);
  }
}
