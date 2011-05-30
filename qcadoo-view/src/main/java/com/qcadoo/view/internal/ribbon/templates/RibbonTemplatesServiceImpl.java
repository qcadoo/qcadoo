package com.qcadoo.view.internal.ribbon.templates;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;
import com.qcadoo.view.internal.ribbon.templates.model.TemplateRibbonGroupsPack;

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

        template.parseParameters(parameters);
        ribbon.addGroupsPack(new TemplateRibbonGroupsPack(template, parameters, viewDefinition));
    }

    @Override
    public RibbonTemplate getTemplate(final String templatePlugin, final String templateName) {
        return templates.get(getTemplateFullName(templatePlugin, templateName));
    }

    @Override
    public void addTemplate(final RibbonTemplate ribbonTemplate) {
        templates.put(getTemplateFullName(ribbonTemplate), ribbonTemplate);
    }

    @Override
    public void removeTemplate(final String templatePlugin, final String templateName) {
        templates.remove(getTemplateFullName(templatePlugin, templateName));
    }

    @Override
    public void removeTemplate(final RibbonTemplate ribbonTemplate) {
        templates.remove(getTemplateFullName(ribbonTemplate));
    }

    private String getTemplateFullName(final String templatePlugin, final String templateName) {
        if (templatePlugin == null) {
            return RibbonTemplateParameters.DEFAULT_TEMPLATE_PLUGIN + "." + templateName;
        }
        return templatePlugin + "." + templateName;
    }

    private String getTemplateFullName(final RibbonTemplate ribbonTemplate) {
        return ribbonTemplate.getPlugin() + "." + ribbonTemplate.getName();
    }

}
