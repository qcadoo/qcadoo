package com.qcadoo.plugins.qcadooExport.api.helpers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.qcadoo.plugin.api.PluginUtils;
import com.qcadoo.plugin.api.RunIfEnabled;
import com.qcadoo.plugins.qcadooExport.api.ExportToPDFColumns;
import com.qcadoo.view.api.components.GridComponent;

@Service
public class ExportToPDFColumnsHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExportToPDFColumnsHelper.class);

    @Autowired
    private List<ExportToPDFColumns> services;

    @Autowired
    private ApplicationContext applicationContext;

    public ExportToPDFColumns getColumnsService() {
        AnnotationAwareOrderComparator.sort(services);

        for (ExportToPDFColumns service : services) {
            if (serviceEnabled(service)) {
                return service;
            }
        }
        throw new IllegalStateException("No active PPSReportColumnService found.");
    }

    private <M extends Object & ExportToPDFColumns> boolean serviceEnabled(M service) {
        RunIfEnabled runIfEnabled = service.getClass().getAnnotation(RunIfEnabled.class);

        if (runIfEnabled == null) {
            return true;
        }

        for (String pluginIdentifier : runIfEnabled.value()) {
            if (!PluginUtils.isEnabled(pluginIdentifier)) {
                return false;
            }
        }

        return true;
    }

    public List<String> getColumns(final GridComponent grid) {
        try {
			ExportToPDFColumns service = getColumnsService();

            return service.getColumns(grid);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
        }

        return Lists.newArrayList();
    }

}
