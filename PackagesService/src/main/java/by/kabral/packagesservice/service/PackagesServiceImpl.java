package by.kabral.packagesservice.service;

import by.kabral.packagesservice.client.FormsFeignClient;
import by.kabral.packagesservice.client.UsersFeignClient;
import by.kabral.packagesservice.dto.FormDto;
import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.PackagesListDto;
import by.kabral.packagesservice.dto.TargetUserDto;
import by.kabral.packagesservice.dto.UserDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.mapper.PackagesMapper;
import by.kabral.packagesservice.model.Package;
import by.kabral.packagesservice.repository.PackagesRepository;
import by.kabral.packagesservice.util.validator.PackagesValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.CircuitBreakerName.*;
import static by.kabral.packagesservice.util.Constant.*;
import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.RetryName.*;

@Service
@RequiredArgsConstructor
public class PackagesServiceImpl implements EntitiesService<PackagesListDto, Package, PackageDto> {

  private final PackagesRepository packagesRepository;
  private final PackagesMapper packagesMapper;
  private final PackagesValidator packagesValidator;
  private final UsersFeignClient usersFeignClient;
  private final FormsFeignClient formsFeignClient;

  @Override
  @Transactional(readOnly = true)
  public PackagesListDto findAll() {
    List<Package> packages = packagesRepository.findAll();
    List<PackageDto> packagesDto = packages.stream()
            .map(this::fillPackageDto)
            .toList();

    return PackagesListDto.builder()
            .packages(packagesDto)
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Package findEntity(UUID id) throws EntityNotFoundException {
    return packagesRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format(PACKAGE_NOT_FOUND, id)));
  }

  private PackageDto fillPackageDto(Package entity) {
    UserDto targetUser = usersFeignClient.getUserById(entity.getTargetUserId()).getBody();
    FormDto form = formsFeignClient.getFormById(entity.getFormId()).getBody();

    return PackageDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .targetUser(targetUser)
            .form(form)
            .isPublic(entity.isPublic())
            .createdAt(entity.getCreatedAt())
            .feedbacks(entity.getFeedbacks())
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  public PackageDto findById(UUID id) throws EntityNotFoundException {
    return fillPackageDto(findEntity(id));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_PACKAGE_BREAKER)
  @Retry(name = SAVE_PACKAGE_RETRY)
  public PackageDto save(PackageDto entity) throws EntityValidateException {
    Package thePackage = packagesMapper.toEntity(entity);
    thePackage.setCreatedAt(ZonedDateTime.now(ZoneId.of(UTC_ZONE_NAME)).toLocalDate());

    packagesValidator.validate(thePackage);

    PackageDto savedPackage = fillPackageDto(packagesRepository.save(thePackage));

    if (savedPackage.getId() == null) {
      throw new EntityValidateException(String.format(PACKAGE_NOT_CREATED, entity.getName()));
    }

    return savedPackage;
  }

  @Override
  @Transactional
  @CircuitBreaker(name = UPDATE_PACKAGE_BREAKER)
  @Retry(name = UPDATE_PACKAGE_RETRY)
  public PackageDto update(UUID id, PackageDto entity) throws EntityNotFoundException, EntityValidateException {
    if (!packagesRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(PACKAGE_NOT_FOUND, id));
    }

    Package thePackage = packagesMapper.toEntity(entity);
    packagesValidator.validate(thePackage);

    return fillPackageDto(packagesRepository.save(thePackage));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = DELETE_PACKAGE_BREAKER)
  @Retry(name = DELETE_PACKAGE_RETRY)
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!packagesRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(PACKAGE_NOT_FOUND, id));
    }

    packagesRepository.deleteById(id);

    return id;
  }

  @KafkaListener(
          topics = "${spring.kafka.users-topic-name}",
          groupId = "${spring.kafka.users-group-id}",
          containerFactory = "userKafkaListenerContainerFactory"
  )
  @Transactional
  protected void usersServiceListener(TargetUserDto targetUser) throws EntityValidateException {
    PackageDto thePackage = PackageDto.builder()
            .name(String.format(COMMUNICATION_SKILLS_FORM_NAME,
                    targetUser.getLastname(),
                    targetUser.getFirstname()))
            .targetUser(UserDto.builder()
                    .id(targetUser.getId())
                    .build())
            .form(FormDto.builder()
                    .id(COMMUNICATION_SKILLS_FORM_UUID)
                    .build())
            .createdAt(ZonedDateTime.now(ZoneId.of(UTC_ZONE_NAME)).toLocalDate())
            .build();

    save(thePackage);
  }
}
