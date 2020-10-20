package com.flipkart.utils.dwone.testing.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.flipkart.utils.dwone.AbstractConfiguration;
import com.flipkart.utils.dwone.dropwizard.AbstractDropwizardApplication;
import com.flipkart.utils.dwone.testing.annotations.TestConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.jackson.Jackson;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;

public class IntegrationTestExtension extends AbstractJUnitGuiceExtension {

    static final ObjectMapper objectMapper = Jackson.newObjectMapper(new YAMLFactory());

    @Override
    public Injector createInjector(Class<?> testClass) throws IllegalAccessException, InstantiationException {
        TestConfig testConfig = testClass.getAnnotation(TestConfig.class);
        AbstractDropwizardApplication<? extends AbstractConfiguration> dropwizardApplication = testConfig.applicationClass().newInstance();
        try {
            String configStr = fixture(testConfig.path());
            AbstractConfiguration abstractConfiguration = objectMapper.readValue(configStr, dropwizardApplication.getConfigurationClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Guice.createInjector();
    }
}