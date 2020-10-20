package com.example.employeeportal.resources;

import com.example.employeeportal.dtos.EmployeeCreationRequest;
import com.example.employeeportal.dtos.EmployeeDetailsResponse;
import com.example.employeeportal.entities.Employee;
import com.example.employeeportal.services.EmployeeService;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.hibernate.FlushMode;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;

@Path("/v1/employees")
@Api("/v1/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EmployeeResource {

    private final EmployeeService employeeService;
    private final Clock clock;

    @GET
    @UnitOfWork(readOnly = true, flushMode = FlushMode.ALWAYS)
    @Path("/{emp-id}")
    public EmployeeDetailsResponse getEmployeeDetails(@NotNull @PathParam("emp-id") Integer employeeId) {
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        return new EmployeeDetailsResponse(
                employee.getId(),
                employee.getFullName(),
                Period.between(employee.getJoiningDate(), LocalDate.now(clock))
        );
    }

    @POST
    @UnitOfWork
    public Response createEmployee(@Valid @NotNull EmployeeCreationRequest employeeCreationRequest) {
        Employee employee = employeeService.createEmployee(
                Employee.builder()
                        .fullName(employeeCreationRequest.getFullName())
                        .joiningDate(employeeCreationRequest.getJoiningDate())
                        .build()
        );
        try {
            return Response.created(
                    new URI(String.format("/v1/employees/%d", employee.getId()))
            ).build();
        } catch (URISyntaxException e) {
            throw new WebApplicationException(e, Response.serverError().build());
        }
    }
}
