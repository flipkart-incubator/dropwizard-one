package com.flipkart.utils.dwone.testing.annotations;

import com.flipkart.utils.dwone.AbstractConfiguration;
import com.flipkart.utils.dwone.dropwizard.AbstractDropwizardApplication;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface TestConfig {
    Class<? extends AbstractConfiguration> configClass();

    String path();

    Class<? extends AbstractDropwizardApplication<? extends AbstractConfiguration>> applicationClass();
}
