package com.qcadoo.report.api;

import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ReportService {

    enum ReportType {
        PDF("application/pdf"), XLS("application/xml"), CSV("text/csv"), HTML("text/html");

        private final String mimeType;

        ReportType(final String mimeType) {
            this.mimeType = mimeType;
        }

        public String getMimeType() {
            return mimeType;
        }
    }

    void generateReportForEntity(OutputStream outputStream, String templateName, ReportType type, List<Long> id,
            Map<String, String> userArgs, Locale locale);

    void generateReport(OutputStream outputStream, String templateName, ReportType type, Map<String, Object> parameters,
            Locale locale);

}
