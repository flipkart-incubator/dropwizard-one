package com.flipkart.utils.dwone.dropwizard.modules;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.flipkart.utils.dwone.dropwizard.exceptionmappers.DuplicateRecordExceptionMapper;
import com.flipkart.utils.dwone.dropwizard.exceptionmappers.RecordNotFoundExceptionMapper;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import io.dropwizard.Configuration;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.ext.ExceptionMapper;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DefaultApplicationModule<T extends Configuration> extends DropwizardAwareModule<T> {

    private final Reflections reflections;
    private final Supplier<SessionFactory> sessionFactorySupplier;

    @Override
    public void configure(Binder binder) {
        // Bind Jersey resources:
        reflections.getTypesAnnotatedWith(Path.class).forEach(binder::bind);
        // Bind exception mappers:
        bindExceptionMappers(binder);
    }

    private void bindExceptionMappers(Binder binder) {
        reflections.getSubTypesOf(ExceptionMapper.class).stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .forEach(binder::bind);
        binder.bind(DuplicateRecordExceptionMapper.class);
        binder.bind(RecordNotFoundExceptionMapper.class);
        binder.bind(JsonParseExceptionMapper.class);
        binder.bind(JsonMappingExceptionMapper.class);
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return sessionFactorySupplier.get();
    }

}
