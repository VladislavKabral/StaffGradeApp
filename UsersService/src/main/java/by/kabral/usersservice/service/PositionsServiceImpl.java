package by.kabral.usersservice.service;

import by.kabral.usersservice.dto.PositionDto;
import by.kabral.usersservice.dto.PositionsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.mapper.PositionMapper;
import by.kabral.usersservice.model.Position;
import by.kabral.usersservice.repository.PositionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import static by.kabral.usersservice.util.Message.*;

@Service
@RequiredArgsConstructor
public class PositionsServiceImpl implements EntitiesService<PositionsListDto, Position, PositionDto, PositionDto> {

  private final PositionsRepository positionsRepository;
  private final PositionMapper positionMapper;

  @Override
  @Transactional(readOnly = true)
  public PositionsListDto findAll() {
    return positionMapper.toListDto(positionsRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public Position findEntity(UUID id) throws EntityNotFoundException {
    return positionsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(POSITION_NOT_FOUND));
  }

  @Override
  @Transactional(readOnly = true)
  public PositionDto findById(UUID id) throws EntityNotFoundException {
    return positionMapper.toDto(findEntity(id));
  }

  @Override
  @Transactional
  public PositionDto save(PositionDto entity) {
    return positionMapper.toDto(positionsRepository
            .save(positionMapper.toEntity(entity)));
  }

  @Override
  @Transactional
  public PositionDto update(UUID id, PositionDto entity) throws EntityNotFoundException {
    if (!positionsRepository.existsById(id)) {
      throw new EntityNotFoundException(POSITION_NOT_FOUND);
    }

    Position position = positionMapper.toEntity(entity);
    position.setId(id);
    return positionMapper.toDto(positionsRepository
            .save(position));
  }

  @Override
  @Transactional
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!positionsRepository.existsById(id)) {
      throw new EntityNotFoundException(POSITION_NOT_FOUND);
    }

    positionsRepository.deleteById(id);
    return id;
  }
}
