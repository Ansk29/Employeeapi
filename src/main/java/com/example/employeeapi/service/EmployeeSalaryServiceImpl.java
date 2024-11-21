package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.repo.EmployeeSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSalaryServiceImpl implements EmployeeSalaryService {

    private final EmployeeSalaryRepository salaryRepository;

    @Autowired
    public EmployeeSalaryServiceImpl(EmployeeSalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<EmployeeSalary> getAllSalariesByEmployeeId(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    @Override
    public EmployeeSalary getSalaryByMonthAndYear(Long employeeId, int month, int year) {
        return salaryRepository.findByEmployeeIdAndSalaryDateMonthAndSalaryDateYear(employeeId, month, year);
    }
}
