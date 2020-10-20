package com.flipkart.utils.dwone.testing.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.utils.dwone.AbstractConfiguration;
import com.flipkart.utils.dwone.dropwizard.AbstractDropwizardApplication;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reflections.Reflections;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(IntegrationTestExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

    @Inject
    protected static Liquibase liquibase;

    @Inject
    protected static DataSource dataSource;

    @Inject
    protected static ObjectMapper objectMapper;

    private static boolean hasSetupTestEnv = false;
    private final AbstractDropwizardApplication<? extends AbstractConfiguration> dropwizardApplication;

    public AbstractIntegrationTest(AbstractDropwizardApplication<? extends AbstractConfiguration> dropwizardApplication) {
        this.dropwizardApplication = dropwizardApplication;
    }

    //@BeforeAll
    public static void setUpBeforeAll() {
        if (!hasSetupTestEnv) {
            try {
                liquibase.dropAll();
                liquibase.update((String) null);
            } catch (LiquibaseException e) {
                fail("[ERROR] Could not re-init db for testing");
            }
            hasSetupTestEnv = true;
        }
    }

    //@BeforeEach
    public void setUp() {
        Operation operation = sequenceOf(
                sql("SET FOREIGN_KEY_CHECKS=0"),
                truncate(getTables()),
                sql("SET FOREIGN_KEY_CHECKS=1")
        );
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
    }

    private List<String> getTables() {
        List<String> tableNames = new ArrayList<>();
        for (Package p : getEntityPackages()) {
            tableNames.addAll(
                    getEntityClasses()
                            .stream()
                            .map(clazz -> clazz.getAnnotation(Entity.class))
                            .map(Entity::name)
                            .collect(Collectors.toList())
            );
        }
        return tableNames;
    }

    private List<Package> getEntityPackages() {
        return null;
    }

    public Set<Class<?>> getEntityClasses() {
        Reflections reflections = dropwizardApplication.getReflections();
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        if (entityClasses.isEmpty()) {
            throw new IllegalStateException("Couldn't get any entities through Reflections. Either your codebase does not have any database entities (in that case you add one) or Reflections is configured incorrectly.");
        }
        return entityClasses;
    }

}
