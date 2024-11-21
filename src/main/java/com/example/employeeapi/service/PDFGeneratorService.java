// PDFGeneratorService.java
package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;

import java.io.ByteArrayOutputStream;

public interface PDFGeneratorService {

    // Method to generate a PDF given an EmployeeSalary and ByteArrayOutputStream
    void generateSalarySlipPDF(EmployeeSalary salary, ByteArrayOutputStream outputStream);
}
