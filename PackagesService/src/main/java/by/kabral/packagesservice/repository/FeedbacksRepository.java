package by.kabral.packagesservice.repository;

import by.kabral.packagesservice.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedback, UUID> {
}
