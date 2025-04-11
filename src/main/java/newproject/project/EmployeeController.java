package newproject.project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
// @RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;


    // ✅ Create a new employee
    @PostMapping("/create")
    public EmployeeEntity createEmployee(@RequestBody EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    // ✅ Get all employees
    @GetMapping("/getemp")
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ Get employee by ID
    @GetMapping("/getemp{id}")
    public EmployeeEntity getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    // ✅ Update employee
    @PutMapping("/updateemp/{id}")
    public EmployeeEntity updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity updatedEmployee) {
        EmployeeEntity existing = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setName(updatedEmployee.getName());
        existing.setGender(updatedEmployee.getGender());
        existing.setBloodGroup(updatedEmployee.getBloodGroup());
        existing.setRole(updatedEmployee.getRole());
        return employeeRepository.save(existing);
    }

    // ✅ Delete employee
    @DeleteMapping("/deleteemp{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return "Employee deleted with ID: " + id;
    }

    // ✅ Assign a role to an employee
   
    
    @PostMapping("/{employeeId}/role/{roleId}")
    public EmployeeEntity assignRoleToEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long roleId) {

        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        employee.setRole(role);
        return employeeRepository.save(employee);
    }

}
