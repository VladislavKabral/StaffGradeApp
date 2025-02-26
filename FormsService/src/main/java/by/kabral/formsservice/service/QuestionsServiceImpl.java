package by.kabral.formsservice.service;

import by.kabral.formsservice.dto.QuestionDto;
import by.kabral.formsservice.dto.QuestionsListDto;
import by.kabral.formsservice.exception.EntityNotFoundException;
import by.kabral.formsservice.exception.EntityValidateException;
import by.kabral.formsservice.mapper.QuestionsMapper;
import by.kabral.formsservice.model.Question;
import by.kabral.formsservice.repository.QuestionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static by.kabral.formsservice.util.Message.*;

@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements EntitiesService<QuestionsListDto, Question, QuestionDto> {

  private final QuestionsRepository questionsRepository;
  private final QuestionsMapper questionsMapper;

  @Override
  @Transactional(readOnly = true)
  public QuestionsListDto findAll() {
    return QuestionsListDto.builder()
            .questions(questionsMapper.toListDto(questionsRepository.findAll()))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Question findEntity(UUID id) throws EntityNotFoundException {
    return questionsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(QUESTION_NOT_FOUND, id)));
  }

  @Transactional(readOnly = true)
  public Boolean existsEntities(List<UUID> ids) throws EntityNotFoundException {
    for (UUID id : ids) {
      if (!questionsRepository.existsById(id)) {
        throw new EntityNotFoundException(String.format(QUESTION_NOT_FOUND, id));
      }
    }

    return true;
  }

  @Override
  @Transactional(readOnly = true)
  public QuestionDto findById(UUID id) throws EntityNotFoundException {
    return questionsMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  public QuestionDto save(QuestionDto entity) throws EntityValidateException {
    Question question = questionsMapper.toEntity(entity);
    QuestionDto savedQuestion = questionsMapper.toDto(questionsRepository.save(question));

    if (savedQuestion.getId() == null) {
      throw new EntityValidateException(String.format(QUESTION_NOT_CREATED, question.getText()));
    }

    return savedQuestion;
  }

  @Override
  @Transactional
  public QuestionDto update(UUID id, QuestionDto entity) throws EntityNotFoundException {
    if (!questionsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(QUESTION_NOT_FOUND, id));
    }

    Question question = questionsMapper.toEntity(entity);

    return questionsMapper.toDto(questionsRepository.save(question));
  }

  @Override
  @Transactional
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!questionsRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(QUESTION_NOT_FOUND, id));
    }

    questionsRepository.deleteById(id);
    return id;
  }
}
