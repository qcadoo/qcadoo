package com.qcadoo.report.internal.templates;

import net.sf.jasperreports.engine.JasperReport;

import com.qcadoo.report.api.ReportTemplateService;

public interface InternalReportTemplateService extends ReportTemplateService {

    void addTemplate(String name, JasperReport reportTemplate);

    void removeTemplate(String name);

}
