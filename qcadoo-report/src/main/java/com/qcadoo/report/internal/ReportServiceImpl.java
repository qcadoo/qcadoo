package com.qcadoo.report.internal;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qcadoo.report.api.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

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

        // TODO implement it

        PrintStream p = new PrintStream(outputStream);
        p.println("Hello");

    }

}
