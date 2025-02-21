package by.kabral.packagesservice.service;

import by.kabral.packagesservice.client.FormsFeignClient;
import by.kabral.packagesservice.client.UsersFeignClient;
import by.kabral.packagesservice.dto.FormDto;
import by.kabral.packagesservice.dto.PackageDto;
import by.kabral.packagesservice.dto.PackagesListDto;
import by.kabral.packagesservice.dto.UserDto;
import by.kabral.packagesservice.exception.EntityNotFoundException;
import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.mapper.PackagesMapper;
import by.kabral.packagesservice.model.Package;
import by.kabral.packagesservice.repository.PackagesRepository;
import by.kabral.packagesservice.util.validator.PackagesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static by.kabral.packagesservice.util.Message.*;

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
  public PackageDto save(PackageDto entity) throws EntityValidateException {
    Package thePackage = packagesMapper.toEntity(entity);
    thePackage.setCreatedAt(LocalDate.now());

    packagesValidator.validate(thePackage);

    PackageDto savedPackage = fillPackageDto(packagesRepository.save(thePackage));

    if (savedPackage.getId() == null) {
      throw new EntityValidateException(String.format(PACKAGE_NOT_CREATED, entity.getName()));
    }

    return savedPackage;
  }

  @Override
  @Transactional
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
  public UUID delete(UUID id) throws EntityNotFoundException {
    if (!packagesRepository.existsById(id)) {
      throw new EntityNotFoundException(String.format(PACKAGE_NOT_FOUND, id));
    }

    packagesRepository.deleteById(id);

    return id;
  }
}
