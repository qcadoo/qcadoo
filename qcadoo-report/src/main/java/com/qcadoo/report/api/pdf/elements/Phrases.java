package com.qcadoo.report.api.pdf.elements;

import com.lowagie.text.Phrase;
import com.qcadoo.report.api.FontUtils;

public final class Phrases {

    private Phrases() {

    }

    public static Phrase tableContent(final String content) {
        return new Phrase(content, FontUtils.getDejavuRegular7Dark());
    }

}
