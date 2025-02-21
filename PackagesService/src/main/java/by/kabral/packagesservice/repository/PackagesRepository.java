package by.kabral.packagesservice.repository;

import by.kabral.packagesservice.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PackagesRepository extends JpaRepository<Package, UUID> {
  Optional<Package> findByName(String name);
}
