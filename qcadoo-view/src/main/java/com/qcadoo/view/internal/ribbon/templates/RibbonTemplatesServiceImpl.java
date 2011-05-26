package com.qcadoo.view.internal.ribbon.templates;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;

@Service
public class RibbonTemplatesServiceImpl implements RibbonTemplatesService {

    private final Map<String, RibbonTemplate> templates = new HashMap<String, RibbonTemplate>();

    @Override
    public void applyTemplate(final InternalRibbon ribbon, final RibbonTemplateParameters parameters,
            final ViewDefinition viewDefinition) {

        RibbonTemplate template = templates
                .get(getTemplateFullName(parameters.getTemplatePlugin(), parameters.getTemplateName()));
        if (template == null) {
            throw new IllegalStateException("ribbon template '" + parameters.getTemplatePlugin() + "."
                    + parameters.getTemplateName() + "' not found");
        }

        template.applyTemplate(ribbon, parameters, viewDefinition);
    }

    @Override
    public void addRibbonTemplate(RibbonTemplate ribbonTemplate) {
        templates.put(getTemplateFullName(ribbonTemplate), ribbonTemplate);
    }

    @Override
    public void removeRibbonTemplate(String templatePlugin, String templateName) {
        templates.remove(getTemplateFullName(templatePlugin, templateName));
    }

    @Override
    public void removeRibbonTemplate(RibbonTemplate ribbonTemplate) {
        templates.remove(getTemplateFullName(ribbonTemplate));
    }

    private String getTemplateFullName(final String templatePlugin, final String templateName) {
        return templatePlugin + "." + templateName;
    }

    private String getTemplateFullName(final RibbonTemplate ribbonTemplate) {
        return ribbonTemplate.getPlugin() + "." + ribbonTemplate.getName();
    }

}
