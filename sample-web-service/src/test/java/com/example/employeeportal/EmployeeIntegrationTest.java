package com.example.employeeportal;

import com.example.employeeportal.dtos.EmployeeDetailsResponse;
import com.example.employeeportal.entities.Employee;
import com.example.employeeportal.resources.EmployeeResource;
import com.example.employeeportal.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.utils.dwone.testing.annotations.TestConfig;
import com.flipkart.utils.dwone.testing.junit.AbstractIntegrationTest;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.mockito.Mockito;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.*;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfig(
        configClass = EmployeeConfiguration.class,
        path = "config_testcases.yml",
        applicationClass = EmployeeApplication.class
)
class EmployeeIntegrationTest extends AbstractIntegrationTest {

    private static EmployeeService mockEmployeeService = mock(EmployeeService.class);

    private static final ResourceExtension resources = ResourceExtension.builder().addResource(
            new EmployeeResource(mockEmployeeService, Clock.fixed(Instant.parse("2020-04-01T10:15:30.00Z"),
                    ZoneId.systemDefault())))
            .build();
    private static final ObjectMapper objectMapper = resources.getObjectMapper();

    static Employee employee1;

    public EmployeeIntegrationTest() {
        super(new EmployeeApplication());
    }

    //@BeforeAll
    static void setUp1() throws IOException {
        employee1 = objectMapper.readValue(fixture("fixtures/entity_employee1.json"), Employee.class);
        when(mockEmployeeService.getEmployeeDetails(employee1.getId())).thenReturn(employee1);
    }

    //@AfterAll
    static void tearDown() {
        Mockito.reset(mockEmployeeService);
    }

    //@Test
    void testGetEmployee() throws IOException {
        EmployeeDetailsResponse expectedResponse = new EmployeeDetailsResponse(
                employee1.getId(), "Foo Bar", Period.ofMonths(3)
        );
        WebTarget target = resources.client().target(String.format("/v1/employees/%d", employee1.getId()));
        EmployeeDetailsResponse actualResponse = target.request(MediaType.APPLICATION_JSON_TYPE)
                .buildGet()
                .invoke(EmployeeDetailsResponse.class);
        assertEquals(expectedResponse, actualResponse, "Unexpected response from GET employees API");
    }
}