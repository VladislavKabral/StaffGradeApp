package by.kabral.packagesservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Package {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "target_user_id")
  private UUID targetUserId;

  @Column(name = "form_id")
  private UUID formId;

  @Column(name = "is_public")
  private boolean isPublic;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "thePackage")
  private List<Feedback> feedbacks;
}
