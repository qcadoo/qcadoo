package com.qcadoo.report.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.qcadoo.model.api.Entity;
import com.qcadoo.report.api.pdf.PdfUtil;

public class ReportUtil {

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

    public static void sentTranslatedFileName(final Entity entity, final String fileName, final String suffix,
            final String extension, final HttpServletResponse response) {
        Object date = entity.getField("date");
        String translatedFileName = fileName + "_" + PdfUtil.D_T_F.format((Date) date) + "_" + suffix + extension;
        response.setHeader("Content-disposition", "attachment; filename=" + translatedFileName);
    }

}
