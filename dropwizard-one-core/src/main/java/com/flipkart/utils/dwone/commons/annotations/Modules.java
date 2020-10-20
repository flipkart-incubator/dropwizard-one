package com.flipkart.utils.dwone.commons.annotations;

import com.flipkart.utils.dwone.AbstractConfiguration;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface Modules {
    Class<? extends DropwizardAwareModule<? extends AbstractConfiguration>>[] value();
}
