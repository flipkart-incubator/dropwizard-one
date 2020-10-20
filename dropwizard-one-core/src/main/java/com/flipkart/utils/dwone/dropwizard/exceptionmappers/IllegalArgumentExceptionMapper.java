package com.flipkart.utils.dwone.dropwizard.exceptionmappers;

import com.flipkart.utils.dwone.dropwizard.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        Response.Status status = BAD_REQUEST;
        String message = format("Request could not be processed - %s!", exception.getMessage());

        ExceptionResponse errorResponse = new ExceptionResponse();
        ExceptionResponse.Error error = new ExceptionResponse.Error(status.getReasonPhrase(), message);
        errorResponse.add(error);

        return Response.status(status).entity(errorResponse).type(APPLICATION_JSON_TYPE).build();
    }
}
