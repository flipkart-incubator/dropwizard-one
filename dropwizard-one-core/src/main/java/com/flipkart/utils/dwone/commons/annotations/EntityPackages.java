package com.flipkart.utils.dwone.commons.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EntityPackages {
    String[] value();
}
