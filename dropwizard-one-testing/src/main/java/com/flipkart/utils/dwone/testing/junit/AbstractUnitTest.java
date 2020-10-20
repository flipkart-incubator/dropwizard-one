package com.flipkart.utils.dwone.testing.junit;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(UnitTestExtension.class)
public abstract class AbstractUnitTest {
    @Inject
    protected static ObjectMapper objectMapper;
}
