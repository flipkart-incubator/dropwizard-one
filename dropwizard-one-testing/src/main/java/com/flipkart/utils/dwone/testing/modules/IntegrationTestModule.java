package com.flipkart.utils.dwone.testing.modules;

import com.flipkart.utils.dwone.testing.junit.AbstractIntegrationTest;
import com.google.inject.Provides;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Guice module to run integration test cases
 */
public class IntegrationTestModule extends UnitTestModule {
    private final PoolProperties poolProperties;

    public IntegrationTestModule(PoolProperties poolProperties) {
        super();
        this.poolProperties = poolProperties;
    }

    @Override
    protected void configure() {
        requestStaticInjection(AbstractIntegrationTest.class);
    }

    @Provides
    @Singleton
    public Liquibase provideLiquibase(DataSource dataSource) throws LiquibaseException, SQLException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
        return new Liquibase("./migrations.xml", new ClassLoaderResourceAccessor(), database);
    }

    @Provides
    @Singleton
    public DataSource provideDataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
    }

    @Provides
    @Singleton
    public PoolProperties providePoolProperties() {
        return poolProperties;
    }
}
