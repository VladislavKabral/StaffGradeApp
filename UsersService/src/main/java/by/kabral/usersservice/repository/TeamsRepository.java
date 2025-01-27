package by.kabral.usersservice.repository;

import by.kabral.usersservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamsRepository extends JpaRepository<Team, UUID> {
}
