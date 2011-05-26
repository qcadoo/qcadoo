package com.qcadoo.view.internal.ribbon.templates;

import com.qcadoo.view.internal.api.ViewDefinition;
import com.qcadoo.view.internal.ribbon.model.InternalRibbon;
import com.qcadoo.view.internal.ribbon.templates.model.RibbonTemplate;

public interface RibbonTemplatesService {

    void applyTemplate(InternalRibbon ribbon, RibbonTemplateParameters parameters, ViewDefinition viewDefinition);

    void addRibbonTemplate(RibbonTemplate ribbonTemplate);

    void removeRibbonTemplate(String templatePlugin, String templateName);

    void removeRibbonTemplate(RibbonTemplate ribbonTemplate);
}
