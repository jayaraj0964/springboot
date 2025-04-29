package newproject.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import newproject.project.model.EmployeeEntity;
import newproject.project.repository.EmployeeRepository;

@Slf4j
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEntity savEmployeeEntity(EmployeeEntity employeeEntity){
        EmployeeEntity employees = employeeRepository.save(employeeEntity);
        log.info("Created employee with ID: {}", employees.getId());
        return employees;

    }
    
}
