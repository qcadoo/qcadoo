package com.qcadoo.plugin.api;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Determines execution of annotated (type's) method. Run method(s) only if all the plugins with given identifiers are enabled.
 * 
 * This annotation also works with aspects and advices.
 * 
 * @since 1.1.7
 */
@Retention(RUNTIME)
@Target(value = { TYPE, METHOD })
public @interface RunIfEnabled {

    /**
     * Plugin identifiers
     * 
     * @return plugin identifiers
     */
    String[] value();

}
