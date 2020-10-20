package com.flipkart.utils.dwone.dropwizard.exceptionmappers;


import com.flipkart.utils.dwone.commons.exceptions.DuplicateRecordException;
import com.flipkart.utils.dwone.dropwizard.dto.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.flipkart.utils.dwone.commons.CommonConstants.DUPLICATE_RECORD_MESSAGE;
import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public class DuplicateRecordExceptionMapper implements ExceptionMapper<DuplicateRecordException> {

    @Override
    public Response toResponse(DuplicateRecordException e) {
        Response.Status status = BAD_REQUEST;
        String message = format(DUPLICATE_RECORD_MESSAGE, e.getClazz(), e.getRecordId());

        ExceptionResponse errorResponse = new ExceptionResponse();
        ExceptionResponse.Error error = new ExceptionResponse.Error(status.getReasonPhrase(), message);
        errorResponse.add(error);

        return Response.status(status).entity(errorResponse).type(APPLICATION_JSON_TYPE).build();
    }
}
