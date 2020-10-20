package com.flipkart.utils.dwone.persistence;

import lombok.experimental.UtilityClass;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;

@UtilityClass
public class NamingStrategyHelper {

    public void configure(Configuration configuration) {
        Optional<PhysicalNamingStrategy> physicalNamingStrategy = getNamingStrategy(configuration, PHYSICAL_NAMING_STRATEGY);
        physicalNamingStrategy.ifPresent(configuration::setPhysicalNamingStrategy);

        Optional<ImplicitNamingStrategy> implicitNamingStrategy = getNamingStrategy(configuration, IMPLICIT_NAMING_STRATEGY);
        implicitNamingStrategy.ifPresent(configuration::setImplicitNamingStrategy);
    }

    private <K> Optional<K> getNamingStrategy(Configuration configuration, String property) {
        String namingStrategy = configuration.getProperty(property);
        if (isNotBlank(namingStrategy)) {
            try {
                Class namingStrategyClass = Class.forName(namingStrategy);
                return ofNullable((K) namingStrategyClass.newInstance());
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Invalid class provided for Physical Naming strategy", e);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException("Could not instantantiate Physical Naming strategy", e);
            }
        }
        return empty();
    }
}
