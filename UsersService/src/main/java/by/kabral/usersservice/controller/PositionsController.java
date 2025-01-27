package by.kabral.usersservice.controller;

import by.kabral.usersservice.dto.PositionDto;
import by.kabral.usersservice.dto.PositionsListDto;
import by.kabral.usersservice.exception.EntityNotFoundException;
import by.kabral.usersservice.service.PositionsServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionsController {

  private final PositionsServiceImpl positionsService;

  @GetMapping
  public ResponseEntity<PositionsListDto> getAllPositions() {
    return new ResponseEntity<>(positionsService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PositionDto> getPositionById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(positionsService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PositionDto> savePosition(@RequestBody @Valid PositionDto positionDto) {
    return new ResponseEntity<>(positionsService.save(positionDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PositionDto> updatePosition(@PathVariable("id") UUID id,
                                                    @RequestBody @Valid PositionDto positionDto) throws EntityNotFoundException {
    return new ResponseEntity<>(positionsService.update(id, positionDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> deletePosition(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(positionsService.delete(id), HttpStatus.OK);
  }
}
