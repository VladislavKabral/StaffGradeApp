package by.kabral.packagesservice.service;

import by.kabral.packagesservice.client.UsersFeignClient;
import by.kabral.packagesservice.dto.FeedbackDto;
import by.kabral.packagesservice.dto.FeedbacksListDto;
import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.UserDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.mapper.FeedbacksMapper;
import by.kabral.packagesservice.mapper.ResponsesMapper;
import by.kabral.packagesservice.model.Feedback;
import by.kabral.packagesservice.model.Response;
import by.kabral.packagesservice.repository.FeedbacksRepository;
import by.kabral.packagesservice.repository.StatusesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.StatusName.*;

@Service
@RequiredArgsConstructor
public class FeedbacksServiceImpl implements EntitiesService<FeedbacksListDto, Feedback, FeedbackDto> {

  private final FeedbacksRepository feedbacksRepository;
  private final StatusesRepository statusesRepository;
  private final PackagesServiceImpl packagesService;
  private final FeedbacksMapper feedbacksMapper;
  private final ResponsesMapper responsesMapper;
  private final UsersFeignClient usersFeignClient;

  @Override
  @Transactional(readOnly = true)
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
  public FeedbackDto save(FeedbackDto entity) throws EntityValidateException, EntityNotFoundException {
    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedback.setStatus(statusesRepository.findByName(IN_PROGRESS));

    Feedback savedFeedback = feedbacksRepository.save(feedback);

    if (savedFeedback.getId() == null) {
      throw new EntityValidateException(String.format(FEEDBACK_NOT_CREATED, entity.getSourceUser().getId()));
    }

    return fillFeedback(savedFeedback);
  }

  @Transactional
  public FeedbackDto complete(UUID id, FeedbackDto entity) throws EntityNotFoundException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedback.setId(id);
    feedback.setStatus(statusesRepository.findByName(COMPLETED));
    feedback.setCompletedAt(LocalDate.now());
    List<Response> responses = responsesMapper.toEntityList(entity.getResponses());
    responses.forEach(response -> response.setFeedback(feedback));
    feedback.setResponses(responses);

    return fillFeedback(feedbacksRepository.save(feedback));
  }

  @Override
  @Transactional
  public FeedbackDto update(UUID id, FeedbackDto entity) throws EntityNotFoundException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = feedbacksMapper.toEntity(entity);
    feedback.setSourceUserId(entity.getSourceUser().getId());
    List<Response> responses = responsesMapper.toEntityList(entity.getResponses());
    responses.forEach(response -> response.setFeedback(feedback));
    feedback.setResponses(responses);

    return fillFeedback(feedbacksRepository.save(feedback));
  }

  @Override
  @Transactional
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!feedbacksRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(FEEDBACK_NOT_FOUND, id));
    }

    Feedback feedback = findEntity(id);
    feedback.setStatus(statusesRepository.findByName(REMOVED));
    feedbacksRepository.save(feedback);

    return id;
  }
}
