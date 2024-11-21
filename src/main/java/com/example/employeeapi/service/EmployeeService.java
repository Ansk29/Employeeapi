package com.example.employeeapi.service;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.LoginRequest;
import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.mapper.EmployeeMapper;
import com.example.employeeapi.repo.EmployeeRepo;
import com.example.employeeapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepo repo;
    private final EmployeeMapper mapper;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder
    private final JwtUtil jwtUtil;

    public String create(EmployeeRequest request) {
        Employee employee = mapper.toEntity(request);

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encryptedPassword);

        repo.save(employee);
        return "Employee created successfully";
    }

    public String loginChecking(LoginRequest req) {
        Employee employee = repo.findByEmail(req.email());

        if (employee == null) {
            return "Invalid email"; // Return message if employee not found
        }

        // Check if the password matches
        if (passwordEncoder.matches(req.password(), employee.getPassword())) {
            // Generate JWT token containing email and employeeId as claims
            String token = jwtUtil.generateToken(employee.getEmail(), employee.getEmployeeId());
            return token;  // Return the token to be used by the frontend
        } else {
            return "Invalid password";  // Return message if password doesn't match
        }
    }
}
