package by.kabral.packagesservice.mapper;

import by.kabral.packagesservice.dto.ResponseDto;
import by.kabral.packagesservice.model.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponsesMapper {

  private final ModelMapper modelMapper;

  public ResponseDto toDto(Response response) {
    return modelMapper.map(response, ResponseDto.class);
  }

  public List<ResponseDto> toDtoList(List<Response> responses) {
    return responses.stream()
            .map(this::toDto)
            .toList();
  }
}
