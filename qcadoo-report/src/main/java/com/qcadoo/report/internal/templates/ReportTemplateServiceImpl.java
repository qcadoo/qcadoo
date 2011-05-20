package com.qcadoo.report.internal.templates;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;

public class ReportTemplateServiceImpl implements InternalReportTemplateService {

    final Map<String, JasperReport> teplates = new HashMap<String, JasperReport>();

    @Override
    public JasperReport getTemplate(final String name) {
        return teplates.get(name);
    }

    @Override
    public void addTemplate(final String name, final JasperReport reportTemplate) {
        teplates.put(name, reportTemplate);
    }

    @Override
    public void removeTemplate(final String name) {
        teplates.remove(name);
    }

}
