package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;

import java.util.List;

public interface EmployeeSalaryService {

    // Method to fetch all salaries for an employee
    List<EmployeeSalary> getAllSalariesByEmployeeId(Long employeeId);

    // Method to fetch salary by month and year
    EmployeeSalary getSalaryByMonthAndYear(Long employeeId, int month, int year);
}
