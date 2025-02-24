package by.kabral.packagesservice.mapper;

import by.kabral.packagesservice.dto.ResponseDto;
import by.kabral.packagesservice.model.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponsesMapper {

  private final ModelMapper modelMapper;

  public ResponseDto toDto(Response response) {
    return modelMapper.map(response, ResponseDto.class);
  }

  public Response toEntity(ResponseDto responseDto) {
    return modelMapper.map(responseDto, Response.class);
  }

  public List<Response> toEntityList(List<ResponseDto> responseDtoList) {
    return responseDtoList.stream()
            .map(this::toEntity)
            .toList();
  }

  public List<ResponseDto> toDtoList(List<Response> responses) {
    if (responses == null) {
      return new ArrayList<>();
    }
    return responses.stream()
            .map(this::toDto)
            .toList();
  }
}
