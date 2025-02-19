package by.kabral.formsservice.repository;

import by.kabral.formsservice.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormsRepository extends JpaRepository<Form, UUID> {
  Optional<Form> findByName(String name);
}
