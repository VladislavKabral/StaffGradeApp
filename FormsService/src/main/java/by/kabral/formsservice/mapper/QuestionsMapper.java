package by.kabral.formsservice.mapper;

import by.kabral.formsservice.dto.QuestionDto;
import by.kabral.formsservice.model.Question;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionsMapper {

  private final ModelMapper modelMapper;

  public Question toEntity(QuestionDto questionDto) {
    return modelMapper.map(questionDto, Question.class);
  }

  public QuestionDto toDto(Question question) {
    return modelMapper.map(question, QuestionDto.class);
  }

  public List<QuestionDto> toListDto(List<Question> questions) {
    return questions.stream().map(this::toDto).toList();
  }
}
