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

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.qcadoo.model.api.Entity;

public final class PdfPageNumbering extends PdfPageEventHelper {

    /** The PdfTemplate that contains the total number of pages. */
    private PdfTemplate total;

    private final String page;

    private final String in;

    private final String company_name;

    private final String address;

    private final String phone_email;

    public PdfPageNumbering(final String page, final String in, final String tax, final String phone, final Entity company) {
        super();
        this.page = page;
        this.in = in;

        if (company != null) {
            StringBuilder companyData = new StringBuilder();

            companyData = companyData.append(company.getStringField("companyFullName"));
            this.company_name = companyData.toString();

            if (company.getStringField("street") != null && company.getStringField("house") != null
                    && company.getStringField("zipCode") != null && company.getStringField("city") != null) {
                companyData.setLength(0);
                companyData = companyData.append(company.getStringField("street"));
                companyData = companyData.append(" ");
                companyData = companyData.append(company.getStringField("house"));
                if (company.getStringField("flat") != null) {
                    companyData = companyData.append("/");
                    companyData = companyData.append(company.getStringField("flat"));
                }
                companyData = companyData.append(", ");
                companyData = companyData.append(company.getStringField("zipCode"));
                companyData = companyData.append(" ");
                companyData = companyData.append(company.getStringField("city"));
                if (company.getStringField("country") != null) {
                    companyData = companyData.append(", ");
                    companyData = companyData.append(company.getStringField("country"));
                }
                this.address = companyData.toString();
            } else
                this.address = "";

            if (company.getStringField("phone") != null) {
                companyData.setLength(0);
                companyData = companyData.append(phone);
                companyData = companyData.append(": ");
                companyData = companyData.append(company.getStringField("phone"));
                if (company.getStringField("email") != null) {
                    companyData = companyData.append(", ");
                    companyData = companyData.append("E-mail: ");
                    companyData = companyData.append(company.getStringField("email"));
                }
                this.phone_email = companyData.toString();
            } else {
                if (company.getStringField("email") != null) {
                    companyData.setLength(0);
                    companyData = companyData.append("E-mail: ");
                    companyData = companyData.append(company.getStringField("email"));
                    this.phone_email = companyData.toString();
                } else
                    this.phone_email = "";
            }
        } else {
            this.company_name = "";
            this.address = "";
            this.phone_email = "";
        }
    }

    /**
     * @see com.lowagie.text.pdf.PdfPageEvent#onOpenDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    @Override
    public void onOpenDocument(final PdfWriter writer, final Document document) {
        total = writer.getDirectContent().createTemplate(100, 100);
        total.setBoundingBox(new Rectangle(-20, -20, 100, 100));
        try {
            PdfUtil.prepareFontsAndColors();
        } catch (Exception e) {
            throw new ExceptionConverter(e);
        }
    }

    /**
     * @see com.lowagie.text.pdf.PdfPageEvent#onStartPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    @Override
    public void onStartPage(final PdfWriter writer, final Document document) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        String text = page + " " + writer.getPageNumber() + " " + in + " ";
        float textBase = document.top() + 22;
        float textSize = PdfUtil.getArial().getWidthPoint(text, 7);
        cb.setColorFill(PdfUtil.getLightColor());
        cb.setColorStroke(PdfUtil.getLightColor());
        cb.beginText();
        cb.setFontAndSize(PdfUtil.getArial(), 7);
        float adjust = PdfUtil.getArial().getWidthPoint("0", 7);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);
        cb.endText();
        cb.addTemplate(total, document.right() - adjust, textBase);
        cb.setLineWidth(1);
        cb.setLineDash(2, 2, 1);
        cb.moveTo(document.left(), document.top() + 12);
        cb.lineTo(document.right(), document.top() + 12);
        cb.stroke();
        cb.restoreState();
    }

    /**
     * @see com.lowagie.text.pdf.PdfPageEvent#onEndPage(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    @Override
    public void onEndPage(final PdfWriter writer, final Document document) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        String text = page + " " + writer.getPageNumber() + " " + in + " ";
        float textBase = document.bottom() - 25;
        float textSize = PdfUtil.getArial().getWidthPoint(text, 7);
        cb.setColorFill(PdfUtil.getLightColor());
        cb.setColorStroke(PdfUtil.getLightColor());
        cb.setLineWidth(1);
        cb.setLineDash(2, 2, 1);
        cb.moveTo(document.left(), document.bottom() - 10);
        cb.lineTo(document.right(), document.bottom() - 10);
        cb.stroke();
        cb.beginText();
        cb.setFontAndSize(PdfUtil.getArial(), 7);
        float adjust = PdfUtil.getArial().getWidthPoint("0", 7);
        cb.setTextMatrix(document.right() - textSize - adjust, textBase);
        cb.showText(text);
        cb.setTextMatrix(document.left(), textBase);
        cb.showText(company_name);
        float companyFooterLine = 10;
        if (address != "") {
            cb.setTextMatrix(document.left(), textBase - companyFooterLine);
            cb.showText(address);
            companyFooterLine += 10;
        }
        if (phone_email != "") {
            cb.setTextMatrix(document.left(), textBase - companyFooterLine);
            cb.showText(phone_email);
        }
        cb.endText();
        cb.addTemplate(total, document.right() - adjust, textBase);
        cb.restoreState();
    }

    /**
     * @see com.lowagie.text.pdf.PdfPageEvent#onCloseDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
     */
    @Override
    public void onCloseDocument(final PdfWriter writer, final Document document) {
        total.beginText();
        total.setFontAndSize(PdfUtil.getArial(), 7);
        total.setTextMatrix(0, 0);
        total.showText(String.valueOf(writer.getPageNumber() - 1));
        total.endText();
    }

}
