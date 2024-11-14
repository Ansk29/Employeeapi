package com.example.employeeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email", nullable=false, unique=true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;  // Password field added here

    @Column(name="title")
    private String title;  // Optional title field (e.g., professor, assistant)

    @Column(name="photograph_path")
    private String photographPath;  // Optional field for photograph path
}