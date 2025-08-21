package in.math2do.practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
  name = "employees",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})
  }
)
public class EmployeeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  private String name;

  @Column(unique = true, nullable = false)
  private String email;
  private String role;
  private int salary;
}
