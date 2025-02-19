package by.kabral.formsservice.repository;

import by.kabral.formsservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, UUID> {
}
