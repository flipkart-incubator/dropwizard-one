package com.example.employeeportal.resources;

import com.example.employeeportal.EmployeeConfiguration;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DefaultResource {

    private final EmployeeConfiguration configuration;

    @GET
    @Path("/")
    public String getWelcomeMessage() {
        return configuration.getWelcomeMessage();
    }

    @GET
    @Path("/errors/{error-code}")
    public String getError(@PathParam("error-code") @NotNull Integer errorCode) {
        return null;
    }
}
