package by.kabral.packagesservice.controller;

import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.PackagesListDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.service.PackagesServiceImpl;
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
@RequiredArgsConstructor
@RequestMapping("/packages")
public class PackagesController {

  private final PackagesServiceImpl packagesService;

  @GetMapping
  public ResponseEntity<PackagesListDto> getPackages() {
    return new ResponseEntity<>(packagesService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PackageDto> getPackageById(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(packagesService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PackageDto> save(@RequestBody @Valid PackageDto packageDto) throws EntityValidateException {
    return new ResponseEntity<>(packagesService.save(packageDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PackageDto> update(@PathVariable("id") UUID id,
                                           @RequestBody @Valid PackageDto packageDto) throws EntityValidateException, EntityNotFoundException {
    return new ResponseEntity<>(packagesService.update(id, packageDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<UUID> delete(@PathVariable("id") UUID id) throws EntityNotFoundException {
    return new ResponseEntity<>(packagesService.delete(id), HttpStatus.OK);
  }
}
