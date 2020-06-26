package com.qcadoo.plugins.qcadooExport.api.services;

import com.qcadoo.model.api.file.FileService;
import com.qcadoo.plugins.qcadooExport.api.ExportToCsv;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExportToCsvService implements ExportToCsv {

    @Value("${exportedCsvSeparator:','}")
    private String exportedCsvSeparator;

    @Autowired
    private FileService fileService;

    public File createExportFile(List<String> columns, List<String> columnNames, List<Map<String, String>> rows,
            String gridName) {
        String date = DateFormat.getDateInstance().format(new Date());
        File file = fileService.createExportFile("export_" + gridName + "_" + date + ".csv");

        BufferedWriter bufferedWriter = null;

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(239);
            fileOutputStream.write(187);
            fileOutputStream.write(191);

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

            boolean firstName = true;

            for (String name : columnNames) {
                if (firstName) {
                    firstName = false;
                } else {
                    bufferedWriter.append(exportedCsvSeparator);
                }

                bufferedWriter.append("\"").append(normalizeString(name)).append("\"");
            }

            bufferedWriter.append("\n");

            addCsvTableCells(bufferedWriter, rows, columns);

            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
        return file;
    }

    private void addCsvTableCells(final BufferedWriter bufferedWriter, final List<Map<String, String>> rows,
            final List<String> columns) throws IOException {
        for (Map<String, String> row : rows) {
            boolean firstValue = true;

            for (String column : columns) {
                if (firstValue) {
                    firstValue = false;
                } else {
                    bufferedWriter.append(exportedCsvSeparator);
                }

                bufferedWriter.append("\"").append(normalizeString(row.get(column))).append("\"");
            }

            bufferedWriter.append("\n");
        }
    }

    private String normalizeString(final String string) {
        if (StringUtils.hasText(string)) {
            return string.replaceAll("\"", "\\\"").replaceAll("\n", " ");
        } else {
            return "";
        }
    }
}
