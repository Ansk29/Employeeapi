package com.example.employeeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.employeeapi.service.PDFGeneratorService;
import com.example.employeeapi.service.PDFGeneratorServiceImpl;

@Configuration
public class PDFConfig {

    @Bean
    public PDFGeneratorService pdfGeneratorService() {
        return new PDFGeneratorServiceImpl();
    }
}
