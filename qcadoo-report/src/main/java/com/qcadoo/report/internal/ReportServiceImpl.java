package com.qcadoo.report.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.report.api.ReportException;
import com.qcadoo.report.api.ReportService;
import com.qcadoo.report.internal.templates.ReportTemplateService;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportTemplateService reportTemplateService;

    @Override
    public byte[] generateReportForEntity(final String templatePlugin, final String templateName, final ReportType type,
            final List<Long> entityIds, final Map<String, String> userArgs, final Locale locale) throws ReportException {

        Map<String, Object> parameters = new HashMap<String, Object>(userArgs);
        parameters.put("EntityIds", entityIds);

        return generateReport(templatePlugin, templateName, type, parameters, locale);
    }

    @Override
    public byte[] generateReport(final String templatePlugin, final String templateName, final ReportType type,
            final Map<String, Object> parameters, final Locale locale) throws ReportException {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Try to generate report [" + type + ", " + templatePlugin + "." + templateName + ", " + parameters + "]");
        }

        JasperReport template = reportTemplateService.getTemplate(templatePlugin, templateName);
        if (template == null) {
            throw new ReportException(ReportException.Type.NO_TEMPLATE_FOUND, templatePlugin + "." + templateName);
        }

        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(template, parameters, new JREmptyDataSource());

            JRExporter exporter = getExporter(type);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);

            exporter.exportReport();

            return stream.toByteArray();

        } catch (JRException e) {
            throw new ReportException(ReportException.Type.GENERATE_REPORT_EXCEPTION, e);
        }
    }

    private JRExporter getExporter(final ReportType type) throws ReportException {
        JRExporter exporter = null;
        switch (type) {
            case PDF:
                exporter = new JRPdfExporter();
                break;
            case XLS:
                exporter = new JRXlsExporter();
                break;
            case CSV:
                exporter = new JRCsvExporter();
                break;
            // case HTML:
            // exporter = new JRHtmlExporter();
            // break;
            default:
                throw new ReportException(ReportException.Type.WRONG_REPORT_TYPE, type.toString());
        }
        return exporter;
    }
}
