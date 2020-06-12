package com.qcadoo.plugins.qcadooExport.api;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ExportToCsv {

    File createExportFile(List<String> columns, List<String> columnNames, List<Map<String, String>> rows, String gridName);
}
