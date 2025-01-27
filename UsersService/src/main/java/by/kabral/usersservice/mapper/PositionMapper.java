package by.kabral.usersservice.mapper;

import by.kabral.usersservice.dto.PositionDto;
import by.kabral.usersservice.dto.PositionsListDto;
import by.kabral.usersservice.model.Position;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionMapper {

  private final ModelMapper modelMapper;

  public PositionDto toDto(Position position) {
    return modelMapper.map(position, PositionDto.class);
  }

  public Position toEntity(PositionDto positionDto) {
    return modelMapper.map(positionDto, Position.class);
  }

  public PositionsListDto toListDto(List<Position> positions) {
    return PositionsListDto.builder()
            .positions(positions.stream().map(this::toDto).toList())
            .build();
  }
}
