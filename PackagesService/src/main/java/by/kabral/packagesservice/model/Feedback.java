package by.kabral.packagesservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "package_id", referencedColumnName = "id")
  private Package thePackage;

  @Column(name = "source_user_id")
  private UUID sourceUserId;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id")
  private Status status;

  @Column(name = "completed_at")
  private LocalDate completedAt;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "feedback")
  private List<Response> responses;
}
