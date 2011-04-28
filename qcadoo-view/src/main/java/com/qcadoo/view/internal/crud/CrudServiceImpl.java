package com.qcadoo.view.internal.crud;

import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.qcadoo.view.api.ViewDefinitionService;
import com.qcadoo.view.api.crud.CrudService;
import com.qcadoo.view.internal.api.InternalViewDefinition;

@Service
public class CrudServiceImpl implements CrudService {

    @Autowired
    private ViewDefinitionService viewDefinitionService;

    @Override
    public ModelAndView prepareView(final String pluginIdentifier, final String viewName, final Map<String, String> arguments,
            final Locale locale) {

        InternalViewDefinition viewDefinition = (InternalViewDefinition) viewDefinitionService.get(pluginIdentifier, viewName);

        ModelAndView modelAndView = new ModelAndView("crud/crudView");

        String context = viewDefinition.translateContextReferences(arguments.get("context"));

        JSONObject jsonContext = new JSONObject();

        if (StringUtils.hasText(context)) {
            try {
                jsonContext = new JSONObject(context);
            } catch (JSONException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        modelAndView.addObject("model", viewDefinition.prepareView(jsonContext, locale));
        modelAndView.addObject("viewName", viewName);
        modelAndView.addObject("pluginIdentifier", pluginIdentifier);
        modelAndView.addObject("context", context);

        boolean popup = false;
        if (arguments.containsKey("popup")) {
            popup = Boolean.parseBoolean(arguments.get("popup"));
        }
        modelAndView.addObject("popup", popup);

        modelAndView.addObject("locale", locale.getLanguage());

        return modelAndView;
    }

    @Override
    public JSONObject performEvent(final String pluginIdentifier, final String viewName, final JSONObject body,
            final Locale locale) {

        InternalViewDefinition viewDefinition = (InternalViewDefinition) viewDefinitionService.get(pluginIdentifier, viewName);

        try {
            return viewDefinition.performEvent(body, locale);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
