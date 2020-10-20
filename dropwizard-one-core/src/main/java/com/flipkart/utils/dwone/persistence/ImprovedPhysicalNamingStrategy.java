package com.flipkart.utils.dwone.persistence;

import org.atteo.evo.inflector.English;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Converts Entity class name to plural and uses snake case for column and table names.
 */
public class ImprovedPhysicalNamingStrategy extends ImprovedNamingStrategy
        implements PhysicalNamingStrategy {

    public static final ImprovedPhysicalNamingStrategy
            INSTANCE =
            new ImprovedPhysicalNamingStrategy();

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    /**
     * Converts entity class name to plural and converts to snake case
     *
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        String tableName = addUnderscores(English.plural(identifier.getText()));
        return new Identifier(tableName, identifier.isQuoted());
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier;
    }

    /**
     * Converts field name to snake case
     *
     * @param identifier
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return new Identifier(addUnderscores(identifier.getText()), identifier.isQuoted());
    }
}