package by.kabral.formsservice.repository;

import by.kabral.formsservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, UUID> {
  Optional<Skill> findByName(String name);
}
