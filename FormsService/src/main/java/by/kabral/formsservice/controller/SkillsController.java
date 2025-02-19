package by.kabral.formsservice.controller;

import by.kabral.formsservice.dto.SkillDto;
import by.kabral.formsservice.dto.SkillsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.service.SkillsServiceImpl;
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
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillsController {

  private final SkillsServiceImpl skillsService;

  @GetMapping
  public ResponseEntity<SkillsListDto> getAllSkills() {
    return new ResponseEntity<>(skillsService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SkillDto> getSkillById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(skillsService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SkillDto> saveSkill(@RequestBody @Valid SkillDto skillDto) throws EntityValidateException {
    return new ResponseEntity<>(skillsService.save(skillDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SkillDto> updateSkill(@PathVariable("id") UUID id,
                                              @RequestBody @Valid SkillDto skillDto) throws EntityValidateException, EntityNotFoundException {
    return new ResponseEntity<>(skillsService.update(id, skillDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deleteSkill(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(skillsService.delete(id), HttpStatus.OK);
  }
}
