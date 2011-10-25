/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.9
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.qcadoo.report.api.pdf.PdfUtil;

public final class ReportUtil {

    public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    public static final String PDF_CONTENT_TYPE = "application/pdf";

    private ReportUtil() {
        // empty
    }

    public static void sentFileAsAttachement(final String path, final String contentType, final HttpServletResponse response) {
        try {
            File file = new File(path);
            InputStream input = new FileInputStream(file);

            response.setContentType(contentType);

            OutputStream output = response.getOutputStream();
            int bytes = IOUtils.copy(input, output);

            response.setContentLength(bytes);

            output.flush();

            IOUtils.closeQuietly(input);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void sentTranslatedFileName(final Date date, final String fileName, final String suffix,
            final String extension, final HttpServletResponse response) {
        String translatedFileName = fileName + "_" + PdfUtil.D_T_F.format(date) + "_" + suffix + extension;
        response.setHeader("Content-disposition", "attachment; filename=" + translatedFileName);
    }
}
