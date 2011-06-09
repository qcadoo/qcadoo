package com.qcadoo.report.internal.jasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataSourceProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperReport;

public class DataSourceProvider implements JRDataSourceProvider {

    @Override
    public boolean supportsGetFieldsOperation() {
        return true;
    }

    @Override
    public JRField[] getFields(final JasperReport report) throws JRException, UnsupportedOperationException {
        return report.getFields();
    }

    @Override
    public JRDataSource create(final JasperReport report) throws JRException {
        return null; // new DataSource(report);
    }

    @Override
    public void dispose(final JRDataSource dataSource) throws JRException {
        // nothing to do
    }

}
