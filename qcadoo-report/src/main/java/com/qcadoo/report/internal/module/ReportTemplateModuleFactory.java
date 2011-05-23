package com.qcadoo.report.internal.module;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.report.internal.templates.ReportTemplateService;

public class ReportTemplateModuleFactory extends ModuleFactory<ReportTemplateModule> {

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Override
    protected ReportTemplateModule parseElement(String pluginIdentifier, Element element) {
        String resource = getRequiredAttribute(element, "resource");
        String name = getRequiredAttribute(element, "name");

        return new ReportTemplateModule(pluginIdentifier, name, new ClassPathResource(pluginIdentifier + "/" + resource),
                reportTemplateService);
    }

    @Override
    public String getIdentifier() {
        return "report-template";
    }

}
