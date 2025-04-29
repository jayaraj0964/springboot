
package newproject.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import newproject.project.model.EmployeeEntity;
import newproject.project.model.Role;
import newproject.project.repository.EmployeeRepository;
import newproject.project.repository.RoleRepository;
import newproject.project.service.EmployeeService;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeService employeeService;

    // Custom exception for better error handling
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    // Create a new employee
    @PostMapping("/postemp")
    public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employee) {
        logger.info("Creating new employee: {}", employee.getName());
        EmployeeEntity employees = employeeRepository.save(employee);
        return employees;
    }

    // Get all employees
    @GetMapping("/getallemp")
    public List<EmployeeEntity> getAllEmployees() {
        logger.info("Fetching all employees");
        List<EmployeeEntity> employees = employeeRepository.findAll();
        logger.debug("Found {} employees", employees.size());
        return employees;
    }

    // Get a specific employee by ID
    @GetMapping("getemp/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable Long employeeId) {
        logger.info("Fetching employee with ID: {}", employeeId);
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        logger.debug("Found employee: {}", employee.getName());
        return employee;
    }

    // Update an employee's details
    @PutMapping("updateemp/{employeeId}")
    public EmployeeEntity updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeEntity updatedEmployee) {
        logger.info("Updating employee with ID: {}", employeeId);
        if (!employeeRepository.existsById(employeeId)) {
            logger.error("Employee not found with ID: {}", employeeId);
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        updatedEmployee.setId(employeeId);
        logger.debug("Saving updated employee: {}", updatedEmployee.getName());
        return employeeRepository.save(updatedEmployee);
    }

    // Delete an employee by ID
    @DeleteMapping("deleteemp/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        logger.info("Deleting employee with ID: {}", employeeId);
        if (!employeeRepository.existsById(employeeId)) {
            logger.error("Employee not found with ID: {}", employeeId);
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        employeeRepository.deleteById(employeeId);
        logger.debug("Deleted employee with ID: {}", employeeId);
    }

    // Create a new role for an employee
    @PostMapping("assignrole/{employeeId}/roles")
    public Role createRoleForEmployee(@PathVariable Long employeeId,  @RequestBody Role role) {
        logger.info("Assigning role to employee ID: {}", employeeId);
        Set<EmployeeEntity> employee = (Set<EmployeeEntity>) employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        role.setEmployees(employee);
        Role savedRole = roleRepository.save(role);
        logger.debug("Assigned role: {} to employee ID: {}", savedRole.getRoleName(), employeeId);
        return savedRole;
    }

    // Get roles of an employee by employeeId
    @GetMapping("getrole/{employeeId}/roles")
    public List<Role> getRolesForEmployee(@PathVariable Long employeeId) {
        logger.info("Fetching roles for employee ID: {}", employeeId);
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        List<Role> roles = (List<Role>) employee.getRoles();
        logger.debug("Found {} roles for employee ID: {}", roles.size(), employeeId);
        return roles;
    }

    @PostMapping("/role")
    public Role postMethodName(@RequestBody Role role) {
        return roleRepository.save(role);
    }
    
}