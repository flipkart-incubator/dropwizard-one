package com.flipkart.utils.dwone.dropwizard.exceptionmappers;


import com.flipkart.utils.dwone.commons.exceptions.RecordNotFoundException;
import com.flipkart.utils.dwone.dropwizard.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.flipkart.utils.dwone.commons.CommonConstants.RECORD_NOT_FOUND_MESSAGE;
import static java.lang.String.format;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Slf4j
@Provider
public class RecordNotFoundExceptionMapper implements ExceptionMapper<RecordNotFoundException> {

    @Override
    public Response toResponse(RecordNotFoundException e) {
        Response.Status status = NOT_FOUND;
        String message = format(RECORD_NOT_FOUND_MESSAGE, e.getClazz().getSimpleName(), e.getId());

        ExceptionResponse errorResponse = new ExceptionResponse();
        ExceptionResponse.Error error = new ExceptionResponse.Error(status.getReasonPhrase(), message);
        errorResponse.add(error);

        return Response.status(status).entity(errorResponse).type(APPLICATION_JSON_TYPE).build();
    }
}
