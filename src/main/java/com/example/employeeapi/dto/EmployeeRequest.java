package com.example.employeeapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {

    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name should not be empty")
    @NotBlank(message = "First name should not be blank")
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be in the correct format")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password should not be empty")
    @NotBlank(message = "Password should not be blank")
    @Size(min = 6, max = 12, message = "Password must be between 6 and 12 characters")
    @JsonProperty("password")
    private String password;

    @JsonProperty("title")
    private String title;  // Optional field for title (e.g., professor, assistant)

    @JsonProperty("photograph_path")
    private String photographPath;  // Optional field for photograph path
}
