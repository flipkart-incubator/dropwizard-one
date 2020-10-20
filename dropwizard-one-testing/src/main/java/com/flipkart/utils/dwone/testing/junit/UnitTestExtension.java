package com.flipkart.utils.dwone.testing.junit;

import com.flipkart.utils.dwone.testing.modules.UnitTestModule;
import com.google.inject.*;
import org.apache.commons.lang3.ArrayUtils;

public class UnitTestExtension extends AbstractJUnitGuiceExtension {

    @Override
    public Injector createInjector(Class<?> testClass) {

        Module unitTestModule = new UnitTestModule();

        Module[] additionalModules = null;
        Module objectMapperModule = null;
        Module[] allModules = ArrayUtils.addAll(additionalModules, unitTestModule, objectMapperModule);

        return Guice.createInjector(allModules);
    }


}