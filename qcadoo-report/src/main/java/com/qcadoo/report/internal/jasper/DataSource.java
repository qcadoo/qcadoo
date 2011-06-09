package com.qcadoo.report.internal.jasper;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class DataSource implements JRDataSource {

    private int i = 5;

    private final String url;

    private final String query;

    public DataSource(final String url, final String query) {
        this.url = url;
        this.query = query;
    }

    @Override
    public boolean next() throws JRException {
        return i-- > 0;
    }

    @Override
    public Object getFieldValue(final JRField jrField) throws JRException {
        if (jrField.getValueClass().equals(Date.class)) {
            return new Date();
        } else if (jrField.getValueClass().equals(BigDecimal.class)) {
            return BigDecimal.ONE;
        } else {
            return url + " -> " + query;
        }
    }

}
