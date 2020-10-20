package com.example.employeeportal.helpers;

import lombok.experimental.UtilityClass;
import org.reflections.Reflections;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public final class EntityHelper {

    /**
     * List of all Hibernate entity classes, exception junction tables mapped using {@link JoinTable} annotation
     *
     * @param entityPkg
     */
    public static Set<Class<?>> getEntityClasses(Package entityPkg) {
        Reflections reflections = new Reflections(entityPkg.getName());
        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        if (entityClasses.isEmpty()) {
            throw new IllegalStateException("Couldn't get any entities through Reflections. Either your codebase does not have any database entities (in that case you add one) or Reflections is configured incorrectly.");
        }
        return entityClasses;
    }

    /**
     * List of all database table names
     */
    public static Set<String> getEntityTableNames(Package entityPkg) {
        Set<Class<?>> entityClasses = getEntityClasses(entityPkg);
        Set<String> tableNames = entityClasses.stream().map(e -> e.getAnnotation(Table.class).name()).collect(Collectors.toSet());
        for (Class entityClass : entityClasses) {
            for (Field field : entityClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(JoinTable.class))
                    continue;
                JoinTable joinTable = field.getAnnotation(JoinTable.class);
                tableNames.add(joinTable.name());
            }
        }
        return tableNames;
    }

}
