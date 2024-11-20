package com.example.employeeapi.controller;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.service.EmployeeSalaryService;
import com.example.employeeapi.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class EmployeeSalaryController {

    private final EmployeeSalaryService salaryService;
    private final JwtUtil jwtUtil;

    public EmployeeSalaryController(EmployeeSalaryService salaryService, JwtUtil jwtUtil) {
        this.salaryService = salaryService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeSalary>> getSalaryHistory(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract the employeeId from the token
        Long employeeId = jwtUtil.extractEmployeeId(token);

        // Fetch salary history using the employeeId
        List<EmployeeSalary> salaryHistory = salaryService.getSalaryHistory(employeeId);

        // Return salary history as the response
        return ResponseEntity.ok(salaryHistory);
    }
}
