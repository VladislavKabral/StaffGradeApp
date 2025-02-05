package by.kabral.usersservice.repository;

import by.kabral.usersservice.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionsRepository extends JpaRepository<Position, UUID> {
  Optional<Position> findByName(String name);
}
