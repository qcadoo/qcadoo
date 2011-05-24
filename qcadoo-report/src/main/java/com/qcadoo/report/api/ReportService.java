package com.qcadoo.report.api;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public interface ReportService {

    enum ReportType {
        // HTML("text/html")
        PDF("application/pdf"), XLS("application/xml"), CSV("text/csv");

        private final String mimeType;

        ReportType(final String mimeType) {
            this.mimeType = mimeType;
        }

        public String getMimeType() {
            return mimeType;
        }
    }

    byte[] generateReportForEntity(String templatePlugin, String templateName, ReportType type, List<Long> entityIds,
            Map<String, String> userArgs, Locale locale) throws ReportException;

    byte[] generateReport(String templatePlugin, String templateName, ReportType type, Map<String, Object> parameters,
            Locale locale) throws ReportException;

}
