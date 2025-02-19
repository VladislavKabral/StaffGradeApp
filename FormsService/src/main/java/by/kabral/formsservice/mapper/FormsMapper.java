package by.kabral.formsservice.mapper;

import by.kabral.formsservice.dto.FormDto;
import by.kabral.formsservice.model.Form;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormsMapper {

  private final ModelMapper modelMapper;

  public Form toEntity(FormDto formDto) {
    return modelMapper.map(formDto, Form.class);
  }

  public FormDto toDto(Form form) {
    return modelMapper.map(form, FormDto.class);
  }

  public List<FormDto> toListDto(List<Form> forms) {
    return forms.stream().map(this::toDto).toList();
  }
}
