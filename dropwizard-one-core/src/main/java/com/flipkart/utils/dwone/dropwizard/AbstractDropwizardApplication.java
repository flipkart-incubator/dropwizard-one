package com.flipkart.utils.dwone.dropwizard;

import com.codahale.metrics.health.HealthCheck;
import com.flipkart.utils.dwone.AbstractConfiguration;
import com.flipkart.utils.dwone.commons.annotations.Modules;
import com.flipkart.utils.dwone.dropwizard.modules.DefaultApplicationModule;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.ws.rs.Path;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Getter
public abstract class AbstractDropwizardApplication<T extends AbstractConfiguration> extends Application<T> {

    protected final Reflections reflections;
    protected final Package basePackage;
    protected final Class<T> configurationClass;
    protected Supplier<Injector> injectorSupplier;

    @SuppressWarnings({"unchecked", "UnstableApiUsage"})
    public AbstractDropwizardApplication(Package basePackage) {
        reflections = new Reflections(basePackage.getName());
        this.basePackage = basePackage;
        configurationClass = (Class<T>) new TypeToken<T>(getClass()) {
        }.getRawType();
    }

    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        // Migrations:
        MigrationsBundle<T> migrationsBundle = createMigrationsBundle();
        bootstrap.addBundle(migrationsBundle);
        // Hibernate:
        HibernateBundle<T> hibernateBundle = createHibernateBundle();
        bootstrap.addBundle(hibernateBundle);
        // Resources:
        SwaggerBundle<T> swaggerBundle = createSwaggerBundle();
        bootstrap.addBundle(swaggerBundle);
        // Guice:
        List<DropwizardAwareModule<T>> modules = new ArrayList<>();
        modules.add(new DefaultApplicationModule<>(reflections, hibernateBundle::getSessionFactory));
        populateApplicationModules(modules);
        GuiceBundle<T> guiceBundle = GuiceBundle.defaultBuilder(configurationClass)
                .modules(modules)
                .allowUnknownFields(false)
                .enableGuiceEnforcer(false)
                .stage(Stage.PRODUCTION)
                .build();
        bootstrap.addBundle(guiceBundle);
        injectorSupplier = guiceBundle::getInjector;
    }

    private void populateApplicationModules(List<DropwizardAwareModule<T>> modules) {
        try {
            Modules applicationModules = this.getClass().getAnnotation(Modules.class);
            if (applicationModules != null && applicationModules.value() != null) {
                for (int i = 0; i < applicationModules.value().length; i++) {
                    Class<? extends DropwizardAwareModule> moduleClass = applicationModules.value()[i];
                    Constructor<? extends DropwizardAwareModule> constructor = moduleClass.getDeclaredConstructor();
                    modules.add(constructor.newInstance());
                }
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("One of more application modules has incorrect signature", e);
        }
    }

    @Override
    public void run(T configuration, Environment environment) {
        Injector injector = injectorSupplier.get();
        reflections.getSubTypesOf(HealthCheck.class).stream()
                .forEach(e -> environment.healthChecks().register(e.getSimpleName(), injector.getInstance(e)));
    }

    private SwaggerBundle<T> createSwaggerBundle() {
        String[] resourcePackages = reflections.getTypesAnnotatedWith(Path.class).stream()
                .map(Class::getPackage)
                .distinct()
                .map(Package::getName)
                .toArray(String[]::new);
        String commaSeparatedResourcePackages = String.join(",", resourcePackages);
        return new SwaggerBundle<T>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(T configuration) {
                SwaggerBundleConfiguration swaggerBundleConfiguration = new SwaggerBundleConfiguration();
                swaggerBundleConfiguration.setResourcePackage(commaSeparatedResourcePackages);
                return swaggerBundleConfiguration;
            }
        };
    }

    private MigrationsBundle<T> createMigrationsBundle() {
        return new MigrationsBundle<T>() {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(T configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }


    private HibernateBundle<T> createHibernateBundle() {
        ImmutableList<Class<?>> entityList = ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
        return new HibernateBundle<T>(entityList, new SessionFactoryFactory()) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(T configuration) {
                return configuration.getDataSourceFactory();
            }
        };
    }

}
