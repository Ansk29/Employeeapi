package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFGeneratorService {

    public byte[] generateSalaryHistoryPDF(String employeeName, List<EmployeeSalary> salaryHistory) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 750);

                // Title
                contentStream.showText("Salary History" );
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Table Header
                contentStream.newLine();
                contentStream.showText("Year       Month       Amount");
                contentStream.newLine();

                // Table Rows
                for (EmployeeSalary salary : salaryHistory) {
                    String year = String.valueOf(salary.getSalaryDate().getYear()); // Extract year from salaryDate
                    String month = salary.getSalaryDate().getMonth().name(); // Extract month as name (e.g., "JANUARY")
                    String line = String.format("%-10s %-10s %.2f",
                            year,
                            month,
                            salary.getSalaryAmount());
                    contentStream.showText(line);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            // Write PDF to ByteArray
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                document.save(outputStream);
                return outputStream.toByteArray();
            }
        }
    }
}
