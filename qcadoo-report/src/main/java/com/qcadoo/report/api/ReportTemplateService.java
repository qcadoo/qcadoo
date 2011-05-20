package com.qcadoo.report.api;

import net.sf.jasperreports.engine.JasperReport;

public interface ReportTemplateService {

    JasperReport getTemplate(String name);

}
