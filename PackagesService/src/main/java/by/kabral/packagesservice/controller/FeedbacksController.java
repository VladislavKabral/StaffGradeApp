package by.kabral.packagesservice.controller;

import by.kabral.packagesservice.dto.FeedbackDto;
import by.kabral.packagesservice.dto.FeedbacksListDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.service.FeedbacksServiceImpl;
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
@RequestMapping("/feedbacks")
public class FeedbacksController {

  private final FeedbacksServiceImpl feedbacksService;

  @GetMapping
  public ResponseEntity<FeedbacksListDto> getFeedbacks() {
    return new ResponseEntity<>(feedbacksService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(feedbacksService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<FeedbackDto> save(@RequestBody @Valid FeedbackDto feedbackDto) throws EntityValidateException, EntityNotFoundException {
    return new ResponseEntity<>(feedbacksService.save(feedbackDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FeedbackDto> update(@PathVariable("id") UUID id,
                                            @RequestBody @Valid FeedbackDto feedbackDto) throws EntityNotFoundException, EntityValidateException {
    return new ResponseEntity<>(feedbacksService.update(id, feedbackDto), HttpStatus.OK);
  }

  @PutMapping("/{id}/complete")
  public ResponseEntity<FeedbackDto> complete(@PathVariable("id") UUID id,
                                              @RequestBody @Valid FeedbackDto feedbackDto) throws EntityNotFoundException, EntityValidateException {
    return new ResponseEntity<>(feedbacksService.complete(id, feedbackDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> delete(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(feedbacksService.delete(id), HttpStatus.OK);
  }
}
