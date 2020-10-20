package com.flipkart.utils.dwone.testing.junit;

import com.google.common.reflect.TypeToken;
import com.google.inject.*;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public abstract class AbstractJUnitGuiceExtension implements TestInstanceFactory, ParameterResolver {
    protected Injector injector;
    private Class<?> testClass;

    @Override
    public Object createTestInstance(TestInstanceFactoryContext testInstanceFactoryContext, ExtensionContext context) {
        try {
            initializeInjector(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return injector.getInstance(testInstanceFactoryContext.getTestClass());
    }

    public void initializeInjector(ExtensionContext context) throws Exception {
        testClass = context.getRequiredTestClass();
        injector = createInjector(testClass);
    }

    public abstract Injector createInjector(Class<?> testClass) throws Exception;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();

        if (extensionContext.getTestMethod().isPresent() && parameter.getName().startsWith("arg")) {
            return false;
        }

        Key<?> key = getKey(parameter);
        try {
            injector.getInstance(key);
            return true;
        } catch (ConfigurationException | ProvisionException ignored) {
            // If we throw a ParameterResolutionException here instead of returning false, we'll block
            // other ParameterResolvers from being able to work.
            return false;
        }
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();
        Key<?> key = getKey(parameter);
        if (injector == null) {
            throw new ParameterResolutionException(String.format("Could not create injector for: %s It has no annotated element.", extensionContext.getDisplayName()));
        }
        return injector.getInstance(key);
    }

    private Key<?> getKey(Parameter parameter) {
        TypeToken<?> classType = TypeToken.of(testClass);
        Type resolvedType = classType.resolveType(parameter.getParameterizedType()).getType();
        return Key.get(resolvedType);
    }

}
