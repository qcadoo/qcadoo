/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.0
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

import static org.apache.commons.io.IOUtils.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.qcadoo.report.api.pdf.PdfUtil;

public final class ReportUtil {

    public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    public static final String PDF_CONTENT_TYPE = "application/pdf";

    private ReportUtil() {
        // empty
    }

    public static void sentFileAsAttachement(final String path, final String contentType, final HttpServletResponse response) {
        response.setContentType(contentType);
        try {

            int bytes = copy(new FileInputStream(new File(path)), response.getOutputStream());

            response.setContentLength(bytes);

        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void sentTranslatedFileName(final Date date, final String fileName, final String suffix,
            final String extension, final HttpServletResponse response) {
        String translatedFileName = fileName + "_" + PdfUtil.D_T_F.format(date) + "_" + suffix + extension;
        response.setHeader("Content-disposition", "inline; filename=" + translatedFileName);
    }
}
