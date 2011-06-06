package com.qcadoo.report.internal.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;

public class QueryExecuter extends JRAbstractQueryExecuter {

    public QueryExecuter(final JRDataset dataset, final Map parameters) {
        super(dataset, parameters);
        // TODO Auto-generated constructor stub
    }

    @Override
    public JRDataSource createDatasource() throws JRException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean cancelQuery() throws JRException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected String getParameterReplacement(final String parameterName) {
        // TODO Auto-generated method stub
        return null;
    }

}
