package by.kabral.packagesservice.service;

import by.kabral.packagesservice.client.FormsFeignClient;
import by.kabral.packagesservice.client.UsersFeignClient;
import by.kabral.packagesservice.dto.FeedbackDto;
import by.kabral.packagesservice.dto.FeedbacksListDto;
import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.QuestionIdsListDto;
import by.kabral.packagesservice.dto.UserDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.mapper.FeedbacksMapper;
import by.kabral.packagesservice.mapper.ResponsesMapper;
import by.kabral.packagesservice.model.Feedback;
import by.kabral.packagesservice.model.Response;
import by.kabral.packagesservice.repository.FeedbacksRepository;
import by.kabral.packagesservice.repository.StatusesRepository;
import by.kabral.packagesservice.util.validator.FeedbacksValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.CircuitBreakerName.*;
import static by.kabral.packagesservice.util.Constant.*;
import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.RetryName.*;
import static by.kabral.packagesservice.util.StatusName.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "feedbacks")
public class FeedbacksServiceImpl implements EntitiesService<FeedbacksListDto, Feedback, FeedbackDto> {

  private final FeedbacksRepository feedbacksRepository;
  private final StatusesRepository statusesRepository;
  private final PackagesServiceImpl packagesService;
  private final FeedbacksMapper feedbacksMapper;
  private final ResponsesMapper responsesMapper;
  private final UsersFeignClient usersFeignClient;
  private final FormsFeignClient formsFeignClient;
  private final FeedbacksValidator feedbacksValidator;

  @Override
  @Transactional(readOnly = true)
  @Cacheable()
  public FeedbacksListDto findAll() {
    List<Feedback> feedbacks = feedbacksRepository.findAll();

    List<FeedbackDto> feedbacksDto = feedbacks.stream()
            .map(feedback -> {
              try {
                return fillFeedback(feedback);
              } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
              }
            })
            .toList();

    return FeedbacksListDto.builder()
            .feedbacks(feedbacksDto)
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Feedback findEntity(UUID id) throws EntityNotFoundException {
    return feedbacksRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id)));
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(key = "#id", unless = "#result == null")
  public FeedbackDto findById(UUID id) throws EntityNotFoundException {
    return fillFeedback(findEntity(id));
  }

  private FeedbackDto fillFeedback(Feedback feedback) throws EntityNotFoundException {
    UserDto sourceUser = usersFeignClient.getUserById(feedback.getSourceUserId()).getBody();
    PackageDto thePackage = packagesService.findById(feedback.getThePackage().getId());

    return FeedbackDto.builder()
            .id(feedback.getId())
            .thePackage(thePackage)
            .sourceUser(sourceUser)
            .status(feedback.getStatus())
            .completedAt(feedback.getCompletedAt())
            .responses(responsesMapper.toDtoList(feedback.getResponses()))
            .build();
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_FEEDBACK_BREAKER)
  @Retry(name = SAVE_FEEDBACK_RETRY)
  @CacheEvict(allEntries = true)
  public FeedbackDto save(FeedbackDto entity) throws EntityValidateException, EntityNotFoundException {
    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedbacksValidator.validate(feedback);
    feedback.setStatus(statusesRepository.findByName(IN_PROGRESS));

    Feedback savedFeedback = feedbacksRepository.save(feedback);

    if (savedFeedback.getId() == null) {
      throw new EntityValidateException(String.format(FEEDBACK_NOT_CREATED, entity.getSourceUser().getId()));
    }

    return fillFeedback(savedFeedback);
  }

  @Transactional
  @CircuitBreaker(name = COMPLETE_FEEDBACK_BREAKER)
  @Retry(name = COMPLETE_FEEDBACK_RETRY)
  @CacheEvict(key = "#id")
  public FeedbackDto complete(UUID id, FeedbackDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedbacksValidator.validate(feedback);
    feedback.setId(id);
    fillFeedback(feedback);
    feedback.setStatus(statusesRepository.findByName(COMPLETED));
    feedback.setCompletedAt(ZonedDateTime.now(ZoneId.of(UTC_ZONE_NAME)).toLocalDate());
    List<Response> responses = responsesMapper.toEntityList(entity.getResponses());
    formsFeignClient.checkExists(QuestionIdsListDto.builder()
            .questionIds(getQuestionsIds(responses))
            .build());
    responses.forEach(response -> response.setFeedback(feedback));
    feedback.setResponses(responses);

    return fillFeedback(feedbacksRepository.save(feedback));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = UPDATE_FEEDBACK_BREAKER)
  @Retry(name = UPDATE_FEEDBACK_RETRY)
  @CacheEvict(key = "#id")
  public FeedbackDto update(UUID id, FeedbackDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedbacksValidator.validate(feedback);
    feedback.setSourceUserId(entity.getSourceUser().getId());
    List<Response> responses = responsesMapper.toEntityList(entity.getResponses());
    formsFeignClient.checkExists(QuestionIdsListDto.builder()
            .questionIds(getQuestionsIds(responses))
            .build());
    responses.forEach(response -> response.setFeedback(feedback));
    feedback.setResponses(responses);

    return fillFeedback(feedbacksRepository.save(feedback));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = DELETE_FEEDBACK_BREAKER)
  @Retry(name = DELETE_FEEDBACK_RETRY)
  @CacheEvict(key = "#id")
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = findEntity(id);
    feedback.setStatus(statusesRepository.findByName(REMOVED));
    feedbacksRepository.save(feedback);

    return id;
  }

  private List<UUID> getQuestionsIds(List<Response> responses) {
    List<UUID> questionsIds = new ArrayList<>();
    responses.forEach(response -> questionsIds.add(response.getQuestionId()));
    return questionsIds;
  }
}
