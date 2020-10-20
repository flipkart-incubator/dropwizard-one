package com.example.employeeportal.services;

import com.example.employeeportal.daos.EmployeeDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class EmployeeServiceTest {

    private static EmployeeDAO mockEmployeeDAO = mock(EmployeeDAO.class);

    private static EmployeeService employeeService = new EmployeeService(mockEmployeeDAO);

    @BeforeAll
    static void before() {

    }

    @Test
    void getEmployeeDetails() {

    }
}