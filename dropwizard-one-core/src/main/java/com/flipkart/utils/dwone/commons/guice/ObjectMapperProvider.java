package com.flipkart.utils.dwone.commons.guice;

import com.fasterxml.jackson.databind.*;
import io.dropwizard.jackson.Jackson;

import javax.inject.Provider;

public class ObjectMapperProvider implements Provider<ObjectMapper> {

    private static ObjectMapper objectMapper = Jackson.newObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public ObjectMapper get() {
        return objectMapper;
    }
}
