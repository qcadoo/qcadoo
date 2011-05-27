package com.qcadoo.view.internal.ribbon.templates;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;

public interface RibbonTemplatesService {

    void applyTemplate(InternalRibbon ribbon, RibbonTemplateParameters parameters, ViewDefinition viewDefinition);

    RibbonTemplate getTemplate(String templatePlugin, String templateName);

    void addTemplate(RibbonTemplate ribbonTemplate);

    void removeTemplate(String templatePlugin, String templateName);

    void removeTemplate(RibbonTemplate ribbonTemplate);
}
