package com.flipkart.utils.dwone.testing.junit;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AbstractResourceUnitTest extends AbstractUnitTest {

    static {
        JerseyGuiceUtils.install((s, serviceLocator) -> null);
    }
}
