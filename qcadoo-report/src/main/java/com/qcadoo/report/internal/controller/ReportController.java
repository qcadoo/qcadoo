package com.qcadoo.report.internal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qcadoo.report.api.ReportService;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "generateReportForEntity", method = RequestMethod.GET)
    public void generateReportForEntity(@RequestParam("templateName") final String requestTemplateName,
            @RequestParam("type") final String requestType, @RequestParam("id") final List<Long> id,
            @RequestParam("additionalArgs") final String requestAdditionalArgs, final HttpServletResponse response,
            final Locale locale) {

        String type = trimRequestArgument(requestType.toUpperCase());
        ReportService.ReportType reportType = ReportService.ReportType.valueOf(type);

        String templateName = trimRequestArgument(requestTemplateName);

        Map<String, String> additionalArgs = convertJsonStringToMap(requestAdditionalArgs);

        try {
            reportService.generateReportForEntity(response.getOutputStream(), templateName, reportType, id, additionalArgs,
                    locale);
            response.setContentType(reportType.getMimeType());
            disableCache(response);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private Map<String, String> convertJsonStringToMap(final String jsonText) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            JSONObject userArgsObject = new JSONObject(jsonText);
            @SuppressWarnings("unchecked")
            Iterator<String> it = userArgsObject.keys();
            while (it.hasNext()) {
                String key = it.next();
                result.put(key, userArgsObject.getString(key));
            }
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return result;
    }

    private void disableCache(final HttpServletResponse response) {
        response.addHeader("Expires", "Tue, 03 Jul 2001 06:00:00 GMT");
        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.addHeader("Pragma", "no-cache");
    }

    private String trimRequestArgument(final String argument) {
        String trimmedArgument = argument.trim();
        if (trimmedArgument.charAt(0) == '\'' || trimmedArgument.charAt(0) == '\"') {
            trimmedArgument = trimmedArgument.substring(1, trimmedArgument.length() - 1);
        }
        return trimmedArgument;
    }
}
