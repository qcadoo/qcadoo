/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.file.FileService;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.crud.CrudService;

@Controller
public class ExportToCsvController {

    private static final String VIEW_NAME_VARIABLE = "viewName";

    private static final String PLUGIN_IDENTIFIER_VARIABLE = "pluginIdentifier";

    private static final String CONTROLLER_PATH = "exportToCsv/{" + PLUGIN_IDENTIFIER_VARIABLE + "}/{" + VIEW_NAME_VARIABLE + "}";

    private static final String EXPORTED_DOCUMENT_SEPARATOR = ",";

    @Autowired
    private FileService fileService;

    @Autowired
    private CrudService crudService;

    @Monitorable(threshold = 500)
    @RequestMapping(value = { CONTROLLER_PATH }, method = RequestMethod.POST)
    @ResponseBody
    public Object generateCsv(@PathVariable(PLUGIN_IDENTIFIER_VARIABLE) final String pluginIdentifier,
            @PathVariable(VIEW_NAME_VARIABLE) final String viewName, @RequestBody final JSONObject body, final Locale locale) {
        try {
            changeMaxResults(body);

            ViewDefinitionState state = crudService.invokeEvent(pluginIdentifier, viewName, body, locale);

            GridComponent grid = (GridComponent) state.getComponentByReference("grid");

            File file = fileService.create("export.csv");

            BufferedWriter output = null;

            try {
                output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));

                boolean firstName = true;

                for (String name : grid.getColumnNames().values()) {
                    if (firstName) {
                        firstName = false;
                    } else {
                        output.append(EXPORTED_DOCUMENT_SEPARATOR);
                    }
                    output.append("\"").append(normalizeString(name)).append("\"");
                }

                output.append("\n");

                List<Map<String, String>> rows;
                if (grid.getSelectedEntitiesIds().isEmpty()) {
                    rows = grid.getColumnValuesOfAllRecords();
                } else {
                    rows = grid.getColumnValuesOfSelectedRecords();
                }

                for (Map<String, String> row : rows) {
                    boolean firstValue = true;
                    for (String value : row.values()) {
                        if (firstValue) {
                            firstValue = false;
                        } else {
                            output.append(EXPORTED_DOCUMENT_SEPARATOR);
                        }
                        output.append("\"").append(normalizeString(value)).append("\"");
                    }

                    output.append("\n");
                }

                output.flush();
            } catch (IOException e) {
                throw new IllegalStateException(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(output);
            }

            state.redirectTo(fileService.getUrl(file.getAbsolutePath()) + "?clean", true, false);

            return crudService.renderView(state);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private String normalizeString(final String string) {
        if (StringUtils.hasText(string)) {
            return string.replaceAll("\"", "\\\"").replaceAll("\n", " ");
        } else {
            return "";
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
