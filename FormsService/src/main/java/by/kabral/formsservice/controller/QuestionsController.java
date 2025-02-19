package by.kabral.formsservice.controller;

import by.kabral.formsservice.dto.QuestionDto;
import by.kabral.formsservice.dto.QuestionsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.service.QuestionsServiceImpl;
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
@RequestMapping("/questions")
public class QuestionsController {

  private final QuestionsServiceImpl questionsService;

  @GetMapping
  public ResponseEntity<QuestionsListDto> getAllQuestions() {
    return new ResponseEntity<>(questionsService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(questionsService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<QuestionDto> saveQuestion(@RequestBody @Valid QuestionDto questionDto) throws EntityValidateException {
    return new ResponseEntity<>(questionsService.save(questionDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<QuestionDto> updateQuestion(@PathVariable("id") UUID id,
                                                    @RequestBody @Valid QuestionDto questionDto) throws EntityNotFoundException {
    return new ResponseEntity<>(questionsService.update(id, questionDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deleteQuestion(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(questionsService.delete(id), HttpStatus.OK);
  }

}
