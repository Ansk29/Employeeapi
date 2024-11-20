package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.repo.EmployeeSalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeSalaryService {

    private final EmployeeSalaryRepository salaryRepository;

    public EmployeeSalaryService(EmployeeSalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public List<EmployeeSalary> getSalaryHistory(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }
}