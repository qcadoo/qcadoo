/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.6
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
package com.qcadoo.report.api.pdf;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.security.api.SecurityService;

public abstract class ReportPdfView extends AbstractPdfView {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private SecurityService securityService;

    private DecimalFormat decimalFormat;

    @Override
    protected final void buildPdfDocument(final Map<String, Object> model, final Document document, final PdfWriter writer,
            final HttpServletRequest request, final HttpServletResponse response) {
        decimalFormat = (DecimalFormat) DecimalFormat.getInstance(LocaleContextHolder.getLocale());
        decimalFormat.setMaximumFractionDigits(3);
        decimalFormat.setMinimumFractionDigits(3);
        String fileName;
        try {
            fileName = addContent(document, model, LocaleContextHolder.getLocale(), writer);
        } catch (DocumentException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        response.setHeader("Content-disposition", "inline; filename=" + fileName + PdfUtil.PDF_EXTENSION);
        writer.addJavaScript("this.print(false);", false);
    }

    @Override
    protected Document newDocument() {
        Document doc = super.newDocument();
        doc.setMargins(40, 40, 60, 60);
        return doc;
    }

    @Override
    protected void prepareWriter(final Map<String, Object> model, final PdfWriter writer, final HttpServletRequest request)
            throws DocumentException {
        super.prepareWriter(model, writer, request);
        writer.setPageEvent(new PdfPageNumbering(getTranslationService().translate("qcadooReport.commons.page.label",
                LocaleContextHolder.getLocale()), getTranslationService().translate("qcadooReport.commons.of.label",
                LocaleContextHolder.getLocale()), getTranslationService().translate("basic.company.tax.label",
                LocaleContextHolder.getLocale()), getTranslationService().translate("basic.company.phone.label",
                LocaleContextHolder.getLocale()), (Entity) model.get("company"), getTranslationService().translate(
                "qcadooReport.commons.generatedBy.label", LocaleContextHolder.getLocale()), securityService.getCurrentUserName()));
    }

    @Override
    protected final void buildPdfMetadata(final Map<String, Object> model, final Document document,
            final HttpServletRequest request) {
        addTitle(document, LocaleContextHolder.getLocale());
        PdfUtil.addMetaData(document);
    }

    protected abstract String addContent(final Document document, final Map<String, Object> model, final Locale locale,
            final PdfWriter writer) throws DocumentException, IOException;

    protected final TranslationService getTranslationService() {
        return translationService;
    }

    protected abstract void addTitle(final Document document, final Locale locale);

    public final DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

}
