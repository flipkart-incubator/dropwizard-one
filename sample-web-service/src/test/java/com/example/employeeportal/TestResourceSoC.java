package com.example.employeeportal;

import com.example.employeeportal.entities.Employee;
import com.example.employeeportal.helpers.EntityHelper;
import com.example.employeeportal.resources.EmployeeResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import java.lang.reflect.Method;
import java.util.Set;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Ensure resource classes maintain the right 'Separation of Concerns'
 */
public class TestResourceSoC {

    private static Reflections reflections = new Reflections(EmployeeResource.class.getPackage().getName(), new MethodAnnotationsScanner());
    private static final Set<Method> resourceMethods = reflections.getMethodsAnnotatedWith(Path.class);
    public static final String CHECKED_EXCEPTION_MESSAGE = "Resource method %s.%s is throwing checked exception(s). To maintain right SoC, ensure resource methods throw runtime exceptions of type %s or it's subtypes.";

    @BeforeAll
    public static void setup() {
        if (resourceMethods.isEmpty()) {
            fail("Reflections failed to find any resource methods. Either your codebase does not have any methods with " + Path.class.getName() + " annotation (in that case you add at least one) or Reflections is configured incorrectly.");
        }
    }

    @SuppressWarnings("SimplifiableJUnitAssertion")
    @Test
    public void ensureResourcesDontThrowCheckedExceptions() {
        for (Method method : resourceMethods) {
            String errorMessage = format(CHECKED_EXCEPTION_MESSAGE, method.getDeclaringClass().getSimpleName(), method.getName(), WebApplicationException.class);
            assertTrue(0 == method.getExceptionTypes().length, errorMessage);
        }
    }

    @Test
    public void ensureResourcesDontExposeDomainObjects() {
        final Set<Class<?>> entityClasses = EntityHelper.getEntityClasses(Employee.class.getPackage());
        for (Method method : resourceMethods) {
            /*
            JavaType type = objectMapper.constructType(method.getGenericReturnType());
            String message = format("Resource method %s.%s returns a hibernate entity. This breaks SoC! Return DTO class in your API.", method.getDeclaringClass().getSimpleName(), method.getName());
            if (type instanceof SimpleType) {
                assertFalse(message, entityClasses.contains(type.getRawClass()));
            } else if (type instanceof MapLikeType) {
                assertFalse(message, entityClasses.contains(type.getKeyType().getRawClass()));
                assertFalse(message, entityClasses.contains(type.getContentType().getRawClass()));
            } else if (type instanceof ArrayType || type instanceof CollectionLikeType) {
                assertFalse(message, entityClasses.contains(type.getContentType().getRawClass()));
            }

             */
        }
    }
}
