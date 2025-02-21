package by.kabral.packagesservice.repository;

import by.kabral.packagesservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatusesRepository extends JpaRepository<Status, UUID> {
  Status findByName(String name);
}
