/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.2
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

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.qcadoo.localization.api.utils.DateUtils;

public final class PdfUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PdfUtil.class);

    private static Font dejavuBold19Light;

    private static Font dejavuBold19Dark;

    private static Font dejavuBold11Dark;

    private static Font dejavuBold11Light;

    private static Font dejavuRegular9Light;

    private static Font dejavuRegular9Dark;

    private static Font dejavuBold9Dark;

    private static Font dejavuRegular10Dark;

    private static Font dejavuBold10Dark;

    private static Color lineLightColor;

    private static Color lineDarkColor;

    private static Color backgroundColor;

    private static Color lightColor;

    private static BaseFont dejavu;

    private static boolean initialized = false;

    private PdfUtil() {

    }

    public static void prepareFontsAndColors() throws DocumentException, IOException {
        if (!initialized) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Pdf fonts and color initialization");
            }
            try {
                FontFactory.register("/fonts/dejaVu/DejaVuSans.ttf");
                dejavu = BaseFont.createFont("/fonts/dejaVu/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (ExceptionConverter e) {
                LOG.warn("Font not found, using embedded font helvetica");
                dejavu = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
            }
            lightColor = new Color(77, 77, 77);
            Color darkColor = new Color(26, 26, 26);
            lineDarkColor = new Color(102, 102, 102);
            lineLightColor = new Color(153, 153, 153);
            backgroundColor = new Color(230, 230, 230);
            dejavuBold19Light = new Font(dejavu, 19);
            dejavuBold19Light.setStyle(Font.BOLD);
            dejavuBold19Light.setColor(lightColor);
            dejavuBold19Dark = new Font(dejavu, 19);
            dejavuBold19Dark.setStyle(Font.BOLD);
            dejavuBold19Dark.setColor(darkColor);
            dejavuRegular9Light = new Font(dejavu, 9);
            dejavuRegular9Light.setColor(lightColor);
            dejavuRegular9Dark = new Font(dejavu, 9);
            dejavuRegular9Dark.setColor(darkColor);
            dejavuBold9Dark = new Font(dejavu, 9);
            dejavuBold9Dark.setColor(darkColor);
            dejavuBold9Dark.setStyle(Font.BOLD);
            dejavuBold11Dark = new Font(dejavu, 11);
            dejavuBold11Dark.setColor(darkColor);
            dejavuBold11Dark.setStyle(Font.BOLD);
            dejavuBold11Light = new Font(dejavu, 11);
            dejavuBold11Light.setColor(lightColor);
            dejavuBold11Light.setStyle(Font.BOLD);
            dejavuRegular10Dark = new Font(dejavu, 10);
            dejavuRegular10Dark.setColor(darkColor);
            dejavuBold10Dark = new Font(dejavu, 10);
            dejavuBold10Dark.setColor(darkColor);
            dejavuBold10Dark.setStyle(Font.BOLD);
            initialized = true;
        }
    }

    public static Color getLineDarkColor() {
        return lineDarkColor;
    }

    public static Font getArialBold19Light() {
        return dejavuBold19Light;
    }

    public static Font getArialBold19Dark() {
        return dejavuBold19Dark;
    }

    public static Font getArialBold11Dark() {
        return dejavuBold11Dark;
    }

    public static Font getArialBold11Light() {
        return dejavuBold11Light;
    }

    public static Font getArialRegular9Light() {
        return dejavuRegular9Light;
    }

    public static Font getArialRegular9Dark() {
        return dejavuRegular9Dark;
    }

    public static Font getArialBold9Dark() {
        return dejavuBold9Dark;
    }

    public static Font getArialRegular10Dark() {
        return dejavuRegular10Dark;
    }

    public static Font getArialBold10Dark() {
        return dejavuBold10Dark;
    }

    public static Color getLineLightColor() {
        return lineLightColor;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static Color getLightColor() {
        return lightColor;
    }

    public static BaseFont getArial() {
        return dejavu;
    }

    public static void addEndOfDocument(final Document document, final PdfWriter writer, final String text) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.setColorFill(lightColor);
        float textBase = document.bottom() - 55;
        float textSize = dejavu.getWidthPoint(text, 7);
        cb.beginText();
        cb.setFontAndSize(dejavu, 7);
        cb.setTextMatrix(document.right() - textSize, textBase);
        cb.showText(text);
        cb.endText();
        cb.restoreState();
    }

    public static void addMetaData(final Document document) {
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("QCADOO");
        document.addCreator("QCADOO");
    }

    public static PdfPTable createTableWithHeader(final int numOfColumns, final List<String> header,
            final boolean lastColumnAligmentToLeft, final int[] columnWidths) {
        PdfPTable table = new PdfPTable(numOfColumns);
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        return setTableProperties(header, lastColumnAligmentToLeft, table);
    }

    public static PdfPTable createTableWithHeader(final int numOfColumns, final List<String> header,
            final boolean lastColumnAligmentToLeft) {
        PdfPTable table = new PdfPTable(numOfColumns);
        return setTableProperties(header, lastColumnAligmentToLeft, table);
    }

    private static PdfPTable setTableProperties(final List<String> header, final boolean lastColumnAligmentToLeft,
            final PdfPTable table) {
        table.setWidthPercentage(100f);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setSpacingBefore(7.0f);
        table.getDefaultCell().setBackgroundColor(backgroundColor);
        table.getDefaultCell().setBorderColor(lineDarkColor);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPadding(5.0f);
        table.getDefaultCell().disableBorderSide(Rectangle.RIGHT);
        int i = 0;
        for (String element : header) {
            i++;
            if (i == header.size() && lastColumnAligmentToLeft) {
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            }
            if (i == header.size()) {
                table.getDefaultCell().enableBorderSide(Rectangle.RIGHT);
            }
            table.addCell(new Phrase(element, dejavuRegular9Dark));
            if (i == 1) {
                table.getDefaultCell().disableBorderSide(Rectangle.LEFT);
            }
            if (i == header.size() && lastColumnAligmentToLeft) {
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            }
        }
        table.getDefaultCell().setBackgroundColor(null);
        table.getDefaultCell().disableBorderSide(Rectangle.RIGHT);
        table.getDefaultCell().setBorderColor(lineLightColor);
        return table;
    }

    public static void addDocumentHeader(final Document document, final String name, final String documenTitle,
            final String documentAuthor, final Date date, final String username) throws DocumentException {
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT, getLocale());
        LineSeparator line = new LineSeparator(3, 100f, lineDarkColor, Element.ALIGN_LEFT, 0);
        document.add(Chunk.NEWLINE);
        Paragraph title = new Paragraph(new Phrase(documenTitle, dejavuBold19Light));
        title.add(new Phrase(" " + name, dejavuBold19Dark));
        title.setSpacingAfter(7f);
        document.add(title);
        document.add(line);
        PdfPTable userAndDate = new PdfPTable(2);
        userAndDate.setWidthPercentage(100f);
        userAndDate.setHorizontalAlignment(Element.ALIGN_LEFT);
        userAndDate.getDefaultCell().setBorderWidth(0);
        Paragraph userParagraph = new Paragraph(new Phrase(documentAuthor, dejavuRegular9Light));
        userParagraph.add(new Phrase(" " + username, dejavuRegular9Dark));
        Paragraph dateParagraph = new Paragraph(df.format(date), dejavuRegular9Light);
        userAndDate.addCell(userParagraph);
        userAndDate.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        userAndDate.addCell(dateParagraph);
        userAndDate.setSpacingAfter(14f);
        document.add(userAndDate);
    }

    public static void addTableCellAsTable(final PdfPTable table, final String label, final Object fieldValue,
            final String nullValue, final Font headerFont, final Font valueFont, final DecimalFormat df) {
        PdfPTable cellTable = new PdfPTable(1);
        cellTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        cellTable.addCell(new Phrase(label, headerFont));
        Object value = fieldValue;
        if (value == null) {
            cellTable.addCell(new Phrase(nullValue, valueFont));
        } else {
            if (value instanceof BigDecimal && df != null) {
                cellTable.addCell(new Phrase(df.format(value), valueFont));
            } else {
                cellTable.addCell(new Phrase(value.toString(), valueFont));
            }
        }
        table.addCell(cellTable);
    }

    public static void addTableCellAsTable(final PdfPTable table, final String label, final Object fieldValue,
            final String nullValue, final Font headerFont, final Font valueFont) {
        addTableCellAsTable(table, label, fieldValue, nullValue, headerFont, valueFont, null);
    }

    public static void addImage(final Document document, final String fileName) {
        try {
            Image img = Image.getInstance(fileName);
            if (img.getWidth() > 515 || img.getHeight() > 370) {
                img.scaleToFit(515, 370);
            }

            document.add(img);
            document.add(Chunk.NEWLINE);
        } catch (BadElementException e) {
            LOG.error(e.getMessage(), e);
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static PdfPTable createPanelTable(final int column) {
        PdfPTable mainData = new PdfPTable(column);
        mainData.setWidthPercentage(100f);
        mainData.getDefaultCell().setBackgroundColor(PdfUtil.getBackgroundColor());
        mainData.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        mainData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        mainData.getDefaultCell().setPadding(8.0f);
        mainData.setTableEvent(new TableBorderEvent());
        return mainData;
    }
}
