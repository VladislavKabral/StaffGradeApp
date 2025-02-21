package by.kabral.packagesservice.util.validator;

import by.kabral.packagesservice.exception.EntityValidateException;
import by.kabral.packagesservice.model.Package;
import by.kabral.packagesservice.repository.PackagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static by.kabral.packagesservice.util.Message.*;

@Component
@RequiredArgsConstructor
public class PackagesValidator implements Validator<Package> {

  private final PackagesRepository packagesRepository;

  @Override
  public void validate(Package entity) throws EntityValidateException {
    Optional<Package> exitingPackage = packagesRepository.findByName(entity.getName());
    if ((exitingPackage.isPresent()) && (!exitingPackage.get().getId().equals(entity.getId()))) {
      throw new EntityValidateException(String.format(PACKAGE_ALREADY_EXISTS, entity.getName()));
    }
  }
}
