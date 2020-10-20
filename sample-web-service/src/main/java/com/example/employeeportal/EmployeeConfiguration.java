package com.example.employeeportal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flipkart.utils.dwone.AbstractConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
public class EmployeeConfiguration extends AbstractConfiguration {

    @Valid
    @NotNull
    private String welcomeMessage;

    @Valid
    @NotNull
    @JsonProperty("database")
    public DataSourceFactory dataSourceFactory;
}
