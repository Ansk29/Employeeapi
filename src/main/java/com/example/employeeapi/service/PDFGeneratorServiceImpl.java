package com.example.employeeapi.service;

import com.example.employeeapi.entity.EmployeeSalary;
import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.repo.EmployeeRepo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGeneratorServiceImpl implements PDFGeneratorService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public PDFGeneratorServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void generateSalarySlipPDF(EmployeeSalary salary, ByteArrayOutputStream outputStream) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Set fonts
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

            // Add title (Salary Slip)
            Paragraph title = new Paragraph("Salary Slip", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));  // Add some space after title

            // Fetch employee details using EmployeeRepo
            Employee employee = employeeRepo.findById(salary.getEmployeeId()).orElse(null);

            if (employee != null) {
                // Create a table with two columns
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100); // Table will take up 100% of the width
                table.setSpacingBefore(10f);   // Space before table
                table.setSpacingAfter(10f);    // Space after table

                // Add rows to the table
                table.addCell(new Phrase("Employee Name:", headerFont));
                table.addCell(new Phrase(employee.getFirstName() + " " + employee.getLastName(), normalFont));

                table.addCell(new Phrase("Employee ID:", headerFont));
                table.addCell(new Phrase(String.valueOf(salary.getEmployeeId()), normalFont));

                table.addCell(new Phrase("Salary Date:", headerFont));
                table.addCell(new Phrase(salary.getSalaryDate().toString(), normalFont));

                table.addCell(new Phrase("Salary Amount:", headerFont));
                table.addCell(new Phrase(String.valueOf(salary.getSalaryAmount()), normalFont));

                table.addCell(new Phrase("Remarks:", headerFont));
                table.addCell(new Phrase(salary.getRemarks() != null ? salary.getRemarks() : "N/A", normalFont));

                // Add the table to the document
                document.add(table);
            } else {
                // If employee not found, show a message
                document.add(new Paragraph("Employee details not found.", normalFont));
            }

            // Add a footer (can be the company name or any note)
            document.add(new Paragraph("\n"));
            Paragraph footer = new Paragraph("ESD project salary slip", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Close the document
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
