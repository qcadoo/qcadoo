package com.qcadoo.tenant.api;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Service for resolving locales.
 * 
 * @since 1.1.7
 */
public interface DefaultLocaleResolver {

    /**
     * This method was made to support obtaining locale whenever you can not get them using Spring's {@link LocaleContextHolder},
     * on example during system or plug-in startup.
     * 
     * The main difference between this metod and {@link LocaleContextHolder#getLocale()} is that the second returns locale for
     * current qcadoo user, choosed by them during log-in.
     * 
     * @return default {@link Locale} for current instance/tenant
     */
    Locale getDefaultLocale();

}
