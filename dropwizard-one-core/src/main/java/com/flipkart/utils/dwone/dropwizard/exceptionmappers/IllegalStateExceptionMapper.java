package com.flipkart.utils.dwone.dropwizard.exceptionmappers;

import com.flipkart.utils.dwone.dropwizard.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;


@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

    public static final String MESSAGE = "Something went wrong !";

    @Override
    public Response toResponse(IllegalStateException exception) {
        Response.Status status = INTERNAL_SERVER_ERROR;

        ExceptionResponse errorResponse = new ExceptionResponse();
        ExceptionResponse.Error error = new ExceptionResponse.Error(status.getReasonPhrase(), MESSAGE);
        errorResponse.add(error);

        return Response.status(status).entity(errorResponse).type(APPLICATION_JSON_TYPE).build();
    }
}
