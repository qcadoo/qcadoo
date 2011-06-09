package com.qcadoo.report.internal.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;

public class QueryExecuterFactory implements JRQueryExecuterFactory {

    public static final String QUERY_LANGUAGE_HQL = "qcadoo-hql";

    public final static String PARAMETER_QCADOO_URL = "QCADOO_URL";

    private final static Object[] QCADOO_BUILTIN_PARAMETERS = { PARAMETER_QCADOO_URL, "org.hibernate.String" };

    @Override
    public Object[] getBuiltinParameters() {
        return QCADOO_BUILTIN_PARAMETERS;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public JRQueryExecuter createQueryExecuter(final JRDataset dataset, final Map parameters) throws JRException {
        return new QueryExecuter(dataset, parameters);
    }

    @Override
    public boolean supportsQueryParameterType(final String className) {
        return true;
    }

}
