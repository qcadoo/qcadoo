package com.qcadoo.report.internal.templates;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;

import org.springframework.stereotype.Service;

@Service
public class ReportTemplateServiceImpl implements ReportTemplateService {

    final Map<String, JasperReport> teplates = new HashMap<String, JasperReport>();

    @Override
    public JasperReport getTemplate(final String plugin, final String name) {
        return teplates.get(generateKey(plugin, name));
    }

    @Override
    public void addTemplate(final String plugin, final String name, final JasperReport reportTemplate) {
        teplates.put(generateKey(plugin, name), reportTemplate);
    }

    @Override
    public void removeTemplate(final String plugin, final String name) {
        teplates.remove(generateKey(plugin, name));
    }

    private String generateKey(final String plugin, final String name) {
        return plugin + "." + name;
    }

}
