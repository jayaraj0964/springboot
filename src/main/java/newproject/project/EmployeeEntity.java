package newproject.project;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empid;
    private String name;
    private String gender;
    private String bloodGroup;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public void setRole(Role role2) {
             throw new UnsupportedOperationException("Unimplemented method 'setRole'");
    }
}
