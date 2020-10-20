package com.example.employeeportal.daos;

import com.example.employeeportal.entities.Employee;
import com.example.employeeportal.entities.Gender;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
class EmployeeDAOTest {

    private static final DAOTestExtension daoTestExtension = DAOTestExtension.newBuilder()
            .addEntityClass(Employee.class)
            .build();

    private final EmployeeDAO employeeDAO = new EmployeeDAO(daoTestExtension.getSessionFactory());

    /**
     * Note that the test case has 2 transactions. This is done purposefully to test if the persistence is working.
     */
    //@Test
    void testPersistence() {
        createEmployee();
    }

    void createEmployee() {
        Employee expectedEmployee = Employee.builder()
                .fullName("Foo Bar")
                .gender(Gender.FEMALE)
                .joiningDate(LocalDate.of(2020, 1, 1))
                .build();
        employeeDAO.persist(expectedEmployee);
        Employee actualEmployee = employeeDAO.get(1);
        assertEquals(expectedEmployee, actualEmployee, "Upon persistence and get, there was loss of information");
    }
}