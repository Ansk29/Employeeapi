package com.example.employeeapi.controller;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.service.EmployeeSalaryService;
import com.example.employeeapi.service.PDFGeneratorService;
import com.example.employeeapi.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class EmployeeSalaryController {

    private final EmployeeSalaryService salaryService;
    private final JwtUtil jwtUtil;
    private final PDFGeneratorService pdfGeneratorService;

    @Autowired
    public EmployeeSalaryController(EmployeeSalaryService salaryService, JwtUtil jwtUtil, PDFGeneratorService pdfGeneratorService) {
        this.salaryService = salaryService;
        this.jwtUtil = jwtUtil;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    // Method to get all salary history for the logged-in employee
    @GetMapping("/history")
    public ResponseEntity<List<EmployeeSalary>> getAllSalaries(
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");
        Long employeeId = jwtUtil.extractEmployeeId(token);

        // Fetch all salary records for the employee
        List<EmployeeSalary> salaryHistory = salaryService.getAllSalariesByEmployeeId(employeeId);

        return ResponseEntity.ok(salaryHistory);
    }

    // Method to download monthly salary slip as a PDF
    @GetMapping("/salary-slip/pdf")
    public ResponseEntity<byte[]> downloadMonthlySalarySlipPDF(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam int month, @RequestParam int year) throws IOException {

        String token = authorizationHeader.replace("Bearer ", "");
        Long employeeId = jwtUtil.extractEmployeeId(token);

        // Use the service method to get salary information
        EmployeeSalary salarySlip = salaryService.getSalaryByMonthAndYear(employeeId, month, year);

        // Generate the PDF for the monthly salary slip
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pdfGeneratorService.generateSalarySlipPDF(salarySlip, byteArrayOutputStream);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SalarySlip_" + month + "_" + year + ".pdf")
                .body(byteArrayOutputStream.toByteArray());
    }
}
