package com.qcadoo.report.internal.jasper;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRStringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryExecuter extends JRAbstractQueryExecuter {

    private static final Log log = LogFactory.getLog(QueryExecuter.class);

    private final String url;

    private String query;

    @SuppressWarnings("rawtypes")
    public QueryExecuter(final JRDataset dataset, final Map parameters) {
        super(dataset, parameters);

        url = (String) getParameterValue(QueryExecuterFactory.PARAMETER_QCADOO_URL);

        if (url == null) {
            log.warn("The supplied org.hibernate.Session object is null.");
        }

        parseQuery();
    }

    @Override
    public JRDataSource createDatasource() throws JRException {
        return new DataSource(url, query);
    }

    @Override
    public void close() {
        // empty
    }

    @Override
    public boolean cancelQuery() throws JRException {
        return false;
    }

    @Override
    protected String getParameterReplacement(final String parameterName) {
        return ':' + getHqlParameterName(parameterName);
    }

    protected String getHqlParameterName(final String parameterName) {
        return '_' + JRStringUtil.getJavaIdentifier(parameterName);
    }

    protected synchronized void createQuery(final String queryString) {
        if (log.isDebugEnabled()) {
            log.info("HQL query: " + queryString);
        }

        Object filterCollection = getParameterValue(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_FILTER_COLLECTION);

        if (filterCollection != null) {
            log.error("Don't know what to do with filterCollection");
        }

        query = queryString;

        // for(Object name : getCollectedParameterNames()) {
        // getValueParameter((String)name);
        // }

    }

}
