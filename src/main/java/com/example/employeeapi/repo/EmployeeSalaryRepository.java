package com.example.employeeapi.repo;

import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.entity.EmployeeSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long> {

    // Existing method to find salary history by employee ID
    List<EmployeeSalary> findByEmployeeId(Long employeeId);


    @Query("SELECT es FROM EmployeeSalary es WHERE es.employeeId = :employeeId AND MONTH(es.salaryDate) = :month AND YEAR(es.salaryDate) = :year")
    EmployeeSalary findByEmployeeIdAndSalaryDateMonthAndSalaryDateYear(
            @Param("employeeId") Long employeeId,
            @Param("month") int month,
            @Param("year") int year
    );

}

