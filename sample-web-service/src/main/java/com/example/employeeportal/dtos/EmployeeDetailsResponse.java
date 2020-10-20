package com.example.employeeportal.dtos;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSnakeCase
public class EmployeeDetailsResponse {
    private Integer id;

    private String fullName;

    private Period tenure;
}
