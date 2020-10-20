package com.example.employeeportal.daos;

import com.example.employeeportal.entities.Employee;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class EmployeeDAO extends AbstractDAO<Employee> {

    @Inject
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Employee get(int empId) {
        return super.get(empId);
    }

    @Override
    public Employee persist(Employee entity) throws HibernateException {
        return super.persist(entity);
    }
}
