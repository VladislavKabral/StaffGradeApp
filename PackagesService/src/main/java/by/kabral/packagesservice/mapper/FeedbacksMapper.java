package by.kabral.packagesservice.mapper;

import by.kabral.packagesservice.dto.FeedbackDto;
import by.kabral.packagesservice.model.Feedback;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedbacksMapper {

  private final ModelMapper modelMapper;

  private FeedbackDto toDto(Feedback feedback) {
    return modelMapper.map(feedback, FeedbackDto.class);
  }

  public List<FeedbackDto> toListDto(List<Feedback> feedbacks) {
    if (feedbacks == null || feedbacks.isEmpty()) {
      return new ArrayList<>();
    }

    return feedbacks.stream()
            .map(this::toDto)
            .toList();
  }

  public Feedback toEntity(FeedbackDto feedbackDto) {
    Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
    feedback.setSourceUserId(feedbackDto.getSourceUser().getId());
    return feedback;
  }

  public List<Feedback> toEntityList(List<FeedbackDto> feedbacks) {
    if (feedbacks == null || feedbacks.isEmpty()) {
      return new ArrayList<>();
    }

    return feedbacks.stream()
            .map(this::toEntity)
            .toList();
  }
}
