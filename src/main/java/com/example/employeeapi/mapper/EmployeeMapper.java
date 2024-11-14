package com.example.employeeapi.mapper;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest request) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .title(request.getTitle())  // Optional field for title
                .photographPath(request.getPhotographPath())  // Optional field for photograph path
                .build();
    }
}
