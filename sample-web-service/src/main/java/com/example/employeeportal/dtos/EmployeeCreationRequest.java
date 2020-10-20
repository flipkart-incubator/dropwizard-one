package com.example.employeeportal.dtos;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSnakeCase
public class EmployeeCreationRequest {
    private String fullName;

    private LocalDate joiningDate;
}
