package com.qcadoo.report.internal;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.report.api.Footer;
import com.qcadoo.report.api.FooterResolver;
import com.qcadoo.security.api.SecurityService;

@Component
public class DefaultFooterResolver implements FooterResolver {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private SecurityService securityService;

    @Override
    public Footer resolveFooter(final Locale locale) {
        String companyName = "";
        String address = "";
        String phoneEmail = "";
        String additionalText = "";

        StringBuilder generatedBy = new StringBuilder();
        generatedBy = generatedBy.append(translationService.translate("qcadooReport.commons.generatedBy.label", locale));
        generatedBy = generatedBy.append(" ");
        generatedBy = generatedBy.append(securityService.getCurrentUserName());

        return new Footer(translationService.translate("qcadooReport.commons.page.label", locale), translationService.translate(
                "qcadooReport.commons.of.label", locale), companyName, address, phoneEmail, generatedBy.toString(),
                additionalText);
    }
}
