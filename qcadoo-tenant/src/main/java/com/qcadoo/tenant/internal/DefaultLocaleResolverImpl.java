package com.qcadoo.tenant.internal;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qcadoo.tenant.api.DefaultLocaleResolver;
import com.qcadoo.tenant.api.Standalone;

@Standalone
@Service
public final class DefaultLocaleResolverImpl implements DefaultLocaleResolver {

    private static final Locale PL = new Locale("pl");

    private static final Locale EN = new Locale("en");

    @Value("${samplesDatasetLocale}")
    private String locale;

    @Override
    public Locale getDefaultLocale() {
        if ("pl".equalsIgnoreCase(locale)) {
            return PL;
        }
        if ("en".equalsIgnoreCase(locale)) {
            return EN;
        }

        return Locale.getDefault();
    }

}
