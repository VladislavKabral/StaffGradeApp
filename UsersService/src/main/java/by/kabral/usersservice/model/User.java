package by.kabral.usersservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "lastname")
  private String lastname;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToOne
  @JoinColumn(name = "manager_id", referencedColumnName = "id")
  private User manager;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id")
  private Status status;

  @ManyToOne
  @JoinColumn(name = "position_id", referencedColumnName = "id")
  private Position position;

  @ManyToOne
  @JoinColumn(name = "team_id", referencedColumnName = "id")
  private Team team;
}
