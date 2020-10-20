package com.flipkart.utils.dwone;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class AbstractConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    public abstract DataSourceFactory getDataSourceFactory();
}
