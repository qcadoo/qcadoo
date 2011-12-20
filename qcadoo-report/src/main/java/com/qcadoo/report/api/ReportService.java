/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.report.api;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ReportService {

    enum ReportType {
        // HTML("text/html", "html"),
        PDF("application/pdf", "pdf"), XML("application/xml", "xml"), CSV("text/csv", "csv"), XLS("application/vnd.ms-excel",
                "xls");

        private final String mimeType;

        private final String extension;

        ReportType(final String mimeType, final String extension) {
            this.mimeType = mimeType;
            this.extension = extension;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getExtension() {
            return extension;
        }
    }

    byte[] generateReportForEntity(String templatePlugin, String templateName, ReportType type, List<Long> entityIds,
            Map<String, String> userArgs, Locale locale) throws ReportException;

    byte[] generateReport(String templatePlugin, String templateName, ReportType type, Map<String, Object> parameters,
            Locale locale) throws ReportException;

    byte[] generateReport(String templateContent, ReportType type, Map<String, Object> parameters, Locale locale)
            throws ReportException;

}
