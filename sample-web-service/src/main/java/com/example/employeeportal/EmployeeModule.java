package com.example.employeeportal;

import com.google.inject.Binder;
import com.google.inject.Provides;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;

import javax.inject.Named;
import java.time.Clock;

public class EmployeeModule extends DropwizardAwareModule<EmployeeConfiguration> {

    @Override
    public void configure(Binder binder) {

    }

    @Named("user")
    @Provides
    public String provideUser() {
        return "Hello";
    }

    @Provides
    public Clock provideClocK() {
        return Clock.systemDefaultZone();
    }
}
