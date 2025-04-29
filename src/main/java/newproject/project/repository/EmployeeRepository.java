package newproject.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import newproject.project.model.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}