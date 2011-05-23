package com.qcadoo.report.internal.templates;

import net.sf.jasperreports.engine.JasperReport;

public interface ReportTemplateService {

    JasperReport getTemplate(String name);

    void addTemplate(String name, JasperReport reportTemplate);

    void removeTemplate(String name);

}
