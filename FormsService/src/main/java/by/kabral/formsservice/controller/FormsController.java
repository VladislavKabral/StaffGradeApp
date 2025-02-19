package by.kabral.formsservice.controller;

import by.kabral.formsservice.dto.FormDto;
import by.kabral.formsservice.dto.FormsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.service.FormsServiceImpl;
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
@RequestMapping("/forms")
public class FormsController {

  private final FormsServiceImpl formsService;

  @GetMapping
  public ResponseEntity<FormsListDto> getAllForms() {
    return new ResponseEntity<>(formsService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormDto> getFormById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(formsService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<FormDto> saveForm(@RequestBody @Valid FormDto formDto) throws EntityValidateException {
    return new ResponseEntity<>(formsService.save(formDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FormDto> updateForm(@PathVariable("id") UUID id,
                                            @RequestBody @Valid FormDto formDto) throws EntityValidateException, EntityNotFoundException {
    return new ResponseEntity<>(formsService.update(id, formDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deleteForm(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(formsService.delete(id), HttpStatus.OK);
  }
}
