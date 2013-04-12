/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.plugins.qcadooExport.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.file.FileService;
import com.qcadoo.report.api.FontUtils;
import com.qcadoo.report.api.FooterResolver;
import com.qcadoo.report.api.pdf.PdfHelper;
import com.qcadoo.report.api.pdf.PdfPageNumbering;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.crud.CrudService;

@Controller
public class ExportToPDFController {

    private static final String VIEW_NAME_VARIABLE = "viewName";

    private static final String PLUGIN_IDENTIFIER_VARIABLE = "pluginIdentifier";

    private static final String CONTROLLER_PATH = "exportToPdf/{" + PLUGIN_IDENTIFIER_VARIABLE + "}/{" + VIEW_NAME_VARIABLE + "}";

    @Autowired
    private CrudService crudService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PdfHelper pdfHelper;

    @Autowired
    private FooterResolver footerResolver;

    @Monitorable(threshold = 500)
    @ResponseBody
    @RequestMapping(value = { CONTROLLER_PATH }, method = RequestMethod.POST)
    public Object generatePdf(@PathVariable(PLUGIN_IDENTIFIER_VARIABLE) final String pluginIdentifier,
            @PathVariable(VIEW_NAME_VARIABLE) final String viewName, @RequestBody final JSONObject body, final Locale locale) {
        try {
            changeMaxResults(body);
            ViewDefinitionState state = crudService.invokeEvent(pluginIdentifier, viewName, body, locale);
            GridComponent grid = (GridComponent) state.getComponentByReference("grid");
            Document document = new Document(PageSize.A4.rotate());
            String date = DateFormat.getDateInstance().format(new Date());
            File file = fileService.createExportFile("export_" + grid.getName() + "_" + date + ".pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);

            writer.setPageEvent(new PdfPageNumbering(footerResolver.resolveFooter(locale)));

            document.setMargins(40, 40, 60, 60);

            document.addTitle("export.pdf");
            pdfHelper.addMetaData(document);
            writer.createXmpMetadata();
            document.open();

            String title = translationService.translate(pluginIdentifier + "." + viewName + ".window.mainTab." + grid.getName()
                    + ".header", locale);

            Date generationDate = new Date();

            pdfHelper.addDocumentHeader(document, "", title,
                    translationService.translate("qcadooReport.commons.generatedBy.label", locale), generationDate);

            int columns = 0;
            List<String> exportToPDFTableHeader = new ArrayList<String>();
            for (String name : grid.getColumnNames().values()) {
                exportToPDFTableHeader.add(name);
                columns++;
            }
            PdfPTable table = pdfHelper.createTableWithHeader(columns, exportToPDFTableHeader, false);

            List<Map<String, String>> rows;
            if (grid.getSelectedEntitiesIds().isEmpty()) {
                rows = grid.getColumnValuesOfAllRecords();
            } else {
                rows = grid.getColumnValuesOfSelectedRecords();
            }

            for (Map<String, String> row : rows) {
                for (String value : row.values()) {
                    table.addCell(new Phrase(value, FontUtils.getDejavuRegular9Dark()));
                }
            }
            document.add(table);
            document.close();

            state.redirectTo(fileService.getUrl(file.getAbsolutePath()) + "?clean", true, false);
            return crudService.renderView(state);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (DocumentException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void changeMaxResults(final JSONObject json) throws JSONException {
        JSONObject component = getComponent(json, getComponentName(json));
        component.getJSONObject("content").put("firstEntity", 0);
        component.getJSONObject("content").put("maxEntities", Integer.MAX_VALUE);
    }

    private JSONObject getComponent(final JSONObject json, final String componentName) throws JSONException {
        String[] path = componentName.split("\\.");

        JSONObject component = json;

        for (String p : path) {
            component = component.getJSONObject("components").getJSONObject(p);
        }

        return component;
    }

    private String getComponentName(final JSONObject body) throws JSONException {
        return body.getJSONObject("event").getString("component");
    }

}
