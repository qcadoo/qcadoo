package com.qcadoo.report.api.pdf;

import java.util.Date;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public interface PdfHelper {

    void addDocumentHeader(final Document document, final String name, final String documenTitle, final String documentAuthor,
            final Date date, final String username) throws DocumentException;

    void addEndOfDocument(final Document document, final PdfWriter writer, final String text);

    void addMetaData(final Document document);

    PdfPTable createPanelTable(final int column);

    void addTableCellAsTable(final PdfPTable table, final String label, final Object fieldValue, final Font headerFont,
            final Font valueFont, final int columns);

    void addTableCellAsTwoColumnsTable(final PdfPTable table, final String label, final Object fieldValue);

    void addTableCellAsOneColumnTable(final PdfPTable table, final String label, final Object fieldValue);

    void addImage(final Document document, final String fileName);

    PdfPTable createTableWithHeader(final int numOfColumns, final List<String> header, final boolean lastColumnAligmentToLeft,
            final int[] columnWidths);

    PdfPTable createTableWithHeader(final int numOfColumns, final List<String> header, final boolean lastColumnAligmentToLeft);

}