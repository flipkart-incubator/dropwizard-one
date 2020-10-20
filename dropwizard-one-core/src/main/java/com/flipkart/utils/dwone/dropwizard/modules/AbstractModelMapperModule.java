package com.flipkart.utils.dwone.dropwizard.modules;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import io.dropwizard.Configuration;
import org.modelmapper.ModelMapper;

import static org.modelmapper.convention.MatchingStrategies.STRICT;

public abstract class AbstractModelMapperModule<T extends Configuration> extends DropwizardAwareModule<T> {

    @Provides
    @Singleton
    public ModelMapper provideModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(STRICT);

        registerPropertyMaps(modelMapper);

        return modelMapper;
    }

    protected abstract void registerPropertyMaps(ModelMapper modelMapper);
}
