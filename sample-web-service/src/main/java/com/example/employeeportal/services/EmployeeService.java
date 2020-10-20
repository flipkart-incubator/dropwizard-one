package com.example.employeeportal.services;

import com.example.employeeportal.daos.EmployeeDAO;
import com.example.employeeportal.entities.Employee;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public Employee getEmployeeDetails(int id) {
        return employeeDAO.get(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeDAO.persist(employee);
    }

}
