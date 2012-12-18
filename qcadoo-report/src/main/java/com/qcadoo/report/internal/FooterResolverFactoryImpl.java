package com.qcadoo.report.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.report.api.FooterResolver;
import com.qcadoo.report.api.FooterResolverFactory;

@Component
public class FooterResolverFactoryImpl implements FooterResolverFactory {

    @Autowired(required = false)
    private FooterResolver footerResolver;

    @Override
    public FooterResolver getResolver() {
        if (footerResolver != null) {
            return footerResolver;
        } else {
            return new DefaultFooterResolver();
        }

    }
}
