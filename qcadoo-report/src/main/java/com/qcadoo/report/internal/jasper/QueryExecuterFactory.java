package com.qcadoo.report.internal.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;

public class QueryExecuterFactory implements JRQueryExecuterFactory {

    @Override
    public Object[] getBuiltinParameters() {
        return new Object[0];
    }

    @Override
    public JRQueryExecuter createQueryExecuter(final JRDataset dataset, final Map parameters) throws JRException {
        return new QueryExecuter(dataset, parameters);
    }

    @Override
    public boolean supportsQueryParameterType(final String className) {
        return true;
    }

}
