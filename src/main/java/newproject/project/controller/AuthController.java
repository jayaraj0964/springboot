package newproject.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import newproject.project.jwt.JwtResponse;
import newproject.project.jwt.JwtUtil;
import newproject.project.model.EmployeeEntity;
import newproject.project.model.LoginRequest;
import newproject.project.model.RegisterRequest;
import newproject.project.model.Role;
import newproject.project.model.User;
import newproject.project.repository.EmployeeRepository;
import newproject.project.repository.UserRepository;
import newproject.project.service.CustomUserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        EmployeeEntity employee = user.getEmployee();
        List<String> roles = employee != null && employee.getRoles() != null
            ? employee.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList())
            : List.of();

        String token = jwtUtil.generateToken(userDetails.getUsername(), roles);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully. Link to employee required.");
    }

    @PostMapping("/link-employee/{username}/{employeeId}")
    public ResponseEntity<?> linkEmployee(@PathVariable String username, @PathVariable Long employeeId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        EmployeeEntity employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
        user.setEmployee(employee);
        userRepository.save(user);
        return ResponseEntity.ok("Employee linked to user successfully");
    }
}