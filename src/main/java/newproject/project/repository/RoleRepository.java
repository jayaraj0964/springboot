
package newproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import newproject.project.model.EmployeeEntity;
import newproject.project.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom query to find roles by employee
    List<Role> findByEmployeesContaining(EmployeeEntity employee);
}
