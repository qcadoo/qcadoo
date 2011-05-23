package com.qcadoo.report.internal;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JasperReport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.report.api.ReportService;
import com.qcadoo.report.internal.templates.ReportTemplateService;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Override
    public void generateReportForEntity(OutputStream outputStream, String templateName, ReportType type, List<Long> id,
            final Map<String, String> userArgs, Locale locale) {

        Map<String, Object> parameters = new HashMap<String, Object>(userArgs);
        parameters.put("EntityIds", id);

        generateReport(outputStream, templateName, type, parameters, locale);
    }

    @Override
    public void generateReport(OutputStream outputStream, String templateName, ReportType type, Map<String, Object> parameters,
            Locale locale) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Try to generate report [" + type + ", " + templateName + ", " + parameters + "]");
        }

        JasperReport template = reportTemplateService.getTemplate(templateName);
        if (template == null) {
            throw new IllegalStateException("No template found: " + templateName);
        }

        // template.
        // JasperPrint jasperPrint = JasperFillManager.fillReport(template, new HashMap(), new JREmptyDataSource());

        // TODO implement it

        PrintStream p = new PrintStream(outputStream);
        p.println("Hello");

    }
}
