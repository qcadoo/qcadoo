package com.qcadoo.report.api.pdf.elements;

import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.qcadoo.report.api.FontUtils;

public final class Headers {

    private Headers() {
    }

    public static Paragraph big(final String text) {
        return paragraph(text, FontUtils.getDejavuBold11Dark(), 8.0f, 8.0f);
    }

    public static Paragraph small(final String text) {
        return paragraph(text, FontUtils.getDejavuBold8Dark(), 2.0f, 2.0f);
    }

    private static Paragraph paragraph(final String text, final Font font, final float marginTop, final float marginBottom) {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setSpacingBefore(marginTop);
        paragraph.setSpacingAfter(marginBottom);
        return paragraph;
    }

}
