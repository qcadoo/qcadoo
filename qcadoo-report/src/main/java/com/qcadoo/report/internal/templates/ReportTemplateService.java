package com.qcadoo.report.internal.templates;

import net.sf.jasperreports.engine.JasperReport;

public interface ReportTemplateService {

    JasperReport getTemplate(String plugin, String name);

    void addTemplate(String plugin, String name, JasperReport reportTemplate);

    void removeTemplate(String plugin, String name);

}
