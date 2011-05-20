package com.qcadoo.report.internal.module;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.core.io.Resource;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.report.internal.templates.InternalReportTemplateService;

public class ReportTemplateModule extends Module {

    private final String pluginIdentifier;

    private final String templateFullName;

    private final Resource templateFile;

    private final InternalReportTemplateService reportTemplateService;

    public ReportTemplateModule(final String pluginIdentifier, final String templateName, final Resource templateFile,
            final InternalReportTemplateService reportTemplateService) {
        this.pluginIdentifier = pluginIdentifier;
        this.templateFullName = pluginIdentifier + "." + templateName;
        this.templateFile = templateFile;
        this.reportTemplateService = reportTemplateService;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        try {
            JasperReport reportTemplate = JasperCompileManager.compileReport(templateFile.getInputStream());
            reportTemplateService.addTemplate(templateFullName, reportTemplate);
        } catch (Exception e) {
            throw new ModuleException(pluginIdentifier, "report-template", e);
        }
    }

    @Override
    public void disable() {
        reportTemplateService.removeTemplate(templateFullName);
    }

}
