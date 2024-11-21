package com.example.employeeapi.controller;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.LoginRequest;
import com.example.employeeapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;




@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeRequest req) {
        return ResponseEntity.ok(employeeService.create(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String token = employeeService.loginChecking(req);

        // If token is not empty, return it
        if (token.startsWith("ey")) { // Check if token starts with "ey" (as JWT tokens typically do)
            return ResponseEntity.ok(new LoginResponse(token)); // Return the token
        }

        // If an error message is returned, send it as a response
        return ResponseEntity.badRequest().body(token);
    }
}
