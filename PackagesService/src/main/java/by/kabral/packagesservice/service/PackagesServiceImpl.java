package by.kabral.packagesservice.service;

import by.kabral.packagesservice.client.FormsFeignClient;
import by.kabral.packagesservice.client.UsersFeignClient;
import by.kabral.packagesservice.dto.FormDto;
import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.PackagesListDto;
import by.kabral.packagesservice.dto.TargetUserDto;
import by.kabral.packagesservice.dto.UserDto;
import by.kabral.packagesservice.dto.UsersIdsListDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.mapper.FeedbacksMapper;
import by.kabral.packagesservice.mapper.PackagesMapper;
import by.kabral.packagesservice.model.Feedback;
import by.kabral.packagesservice.model.Package;
import by.kabral.packagesservice.repository.PackagesRepository;
import by.kabral.packagesservice.util.validator.PackagesValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static by.kabral.packagesservice.util.CircuitBreakerName.*;
import static by.kabral.packagesservice.util.Constant.*;
import static by.kabral.packagesservice.util.Message.*;
import static by.kabral.packagesservice.util.RetryName.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "packages")
public class PackagesServiceImpl implements EntitiesService<PackagesListDto, Package, PackageDto> {

  private final PackagesRepository packagesRepository;
  private final PackagesMapper packagesMapper;
  private final FeedbacksMapper feedbacksMapper;
  private final PackagesValidator packagesValidator;
  private final UsersFeignClient usersFeignClient;
  private final FormsFeignClient formsFeignClient;

  @Override
  @Transactional(readOnly = true)
  @Cacheable()
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
    Map<UUID, UserDto> sourceUsers = getSourceUsersForFeedbacks(entity.getFeedbacks());

    return PackageDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .targetUser(targetUser)
            .form(form)
            .isPublic(entity.isPublic())
            .createdAt(entity.getCreatedAt())
            .feedbacks(feedbacksMapper.toListDto(entity.getFeedbacks(), sourceUsers))
            .build();
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(key = "#id", unless = "#result == null")
  public PackageDto findById(UUID id) throws EntityNotFoundException {
    return fillPackageDto(findEntity(id));
  }

  @Override
  @Transactional
  @CircuitBreaker(name = SAVE_PACKAGE_BREAKER)
  @Retry(name = SAVE_PACKAGE_RETRY)
  @CacheEvict(allEntries = true)
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
  @CacheEvict(key = "#id")
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
  @CacheEvict(key = "#id")
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
  @CacheEvict(allEntries = true)
  public void usersServiceListener(TargetUserDto targetUser) throws EntityValidateException {
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

  private Map<UUID, UserDto> getSourceUsersForFeedbacks(List<Feedback> feedbacks) {
    List<UUID> sourceUsersIds = feedbacks.stream()
            .map(Feedback::getSourceUserId)
            .collect(Collectors.toSet())
            .stream()
            .toList();

    UsersIdsListDto usersIdsList = UsersIdsListDto.builder()
            .ids(sourceUsersIds)
            .build();

    return Objects.requireNonNull(usersFeignClient.getUsersById(usersIdsList)
                    .getBody())
            .getUsers();
  }
}
