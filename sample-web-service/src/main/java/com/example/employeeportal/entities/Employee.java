package com.example.employeeportal.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    @NotNull
    private String fullName;

    @Column(name = "gender")
    @NotNull
    private Gender gender;

    @Column(name = "joining_date")
    @NotNull
    private LocalDate joiningDate;

    @Column(name = "created_at", insertable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    protected LocalDateTime updatedAt;
}
