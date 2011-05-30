package com.qcadoo.plugins.exporttocsv.internal;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.view.api.FileService;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.crud.CrudService;

@Controller
public class ExportToCsvController {

    private static final String VIEW_NAME_VARIABLE = "viewName";

    private static final String PLUGIN_IDENTIFIER_VARIABLE = "pluginIdentifier";

    private static final String CONTROLLER_PATH = "csv/page/{" + PLUGIN_IDENTIFIER_VARIABLE + "}/{" + VIEW_NAME_VARIABLE + "}";

    @Autowired
    private FileService fileService;

    @Autowired
    private CrudService crudService;

    @Monitorable(threshold = 500)
    @RequestMapping(value = { CONTROLLER_PATH }, method = RequestMethod.POST)
    public Object generateCsv(@PathVariable(PLUGIN_IDENTIFIER_VARIABLE) final String pluginIdentifier,
            @PathVariable(VIEW_NAME_VARIABLE) final String viewName, @RequestBody final JSONObject body, final Locale locale) {
        try {
            String componentName = getComponentName(body);

            changeMaxResults(body, componentName);

            ViewDefinitionState state = crudService.invokeEvent(pluginIdentifier, viewName, body, locale);

            GridComponent grid = (GridComponent) state.getComponentByReference(componentName);

            File file = fileService.create("exportToCsv.csv");

            DataOutputStream output = new DataOutputStream(new FileOutputStream(file));

            boolean firstName = true;

            for (String name : grid.getColumnNames(locale).values()) {
                if (firstName) {
                    firstName = false;
                } else {
                    output.writeUTF(",");
                }
                output.writeUTF("\"");
                output.writeUTF(name);
                output.writeUTF("\"");
            }

            output.writeUTF("\n");

            for (Map<String, String> row : grid.getColumnValues(locale)) {
                boolean firstValue = true;

                for (String value : row.values()) {
                    if (firstValue) {
                        firstValue = false;
                    } else {
                        output.writeUTF(",");
                    }
                    output.writeUTF("\"");
                    output.writeUTF(value);
                    output.writeUTF("\"");
                }

                output.writeUTF("\n");
            }

            output.flush();
            output.close();

            state.redirectTo(fileService.getUrl(file.getAbsolutePath()) + "?clean", true, false);

            return crudService.renderView(state);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void changeMaxResults(final JSONObject json, final String componentName) throws JSONException {
        JSONObject component = getComponent(json, componentName);
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
