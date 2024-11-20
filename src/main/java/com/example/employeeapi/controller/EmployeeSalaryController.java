package com.example.employeeapi.controller;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.service.EmployeeSalaryService;
import com.example.employeeapi.service.PDFGeneratorService;
import com.example.employeeapi.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class EmployeeSalaryController {

    private final EmployeeSalaryService salaryService;
    private final JwtUtil jwtUtil;
    private final PDFGeneratorService pdfGeneratorService;

    public EmployeeSalaryController(EmployeeSalaryService salaryService, JwtUtil jwtUtil, PDFGeneratorService pdfGeneratorService) {
        this.salaryService = salaryService;
        this.jwtUtil = jwtUtil;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    // Endpoint to fetch salary history
    @GetMapping
    public ResponseEntity<List<EmployeeSalary>> getSalaryHistory(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long employeeId = jwtUtil.extractEmployeeId(token);
        List<EmployeeSalary> salaryHistory = salaryService.getSalaryHistory(employeeId);
        return ResponseEntity.ok(salaryHistory);
    }

    // Endpoint to download salary history as a PDF
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadSalaryHistoryPDF(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        String token = authorizationHeader.replace("Bearer ", "");
        Long employeeId = jwtUtil.extractEmployeeId(token);

        // Get employee name and salary history
        String employeeName = ""; // Replace this with actual employee name if available
        List<EmployeeSalary> salaryHistory = salaryService.getSalaryHistory(employeeId);

        // Generate the PDF
        byte[] pdfContent = pdfGeneratorService.generateSalaryHistoryPDF(employeeName, salaryHistory);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SalaryHistory.pdf")
                .body(pdfContent);
    }
}
