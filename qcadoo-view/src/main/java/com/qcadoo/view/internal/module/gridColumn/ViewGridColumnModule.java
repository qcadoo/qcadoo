package com.qcadoo.view.internal.module.gridColumn;

import java.util.List;

import com.qcadoo.plugin.api.Module;
import com.qcadoo.plugin.api.ModuleException;
import com.qcadoo.view.internal.api.ComponentPattern;
import com.qcadoo.view.internal.api.InternalViewDefinition;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;
import com.qcadoo.view.internal.components.grid.GridComponentPattern;

public class ViewGridColumnModule extends Module {

    private final String pluginIdentifier;

    private final String extendsViewPlugin;

    private final String extendsViewName;

    private final String extendsComponentName;

    private final List<ViewGridColumnModuleColumnModel> columns;

    private final InternalViewDefinitionService viewDefinitionService;

    public ViewGridColumnModule(final String pluginIdentifier, final String extendsViewPlugin, final String extendsViewName,
            final String extendsComponentName, final List<ViewGridColumnModuleColumnModel> columns,
            final InternalViewDefinitionService viewDefinitionService) {

        this.pluginIdentifier = pluginIdentifier;
        this.extendsViewPlugin = extendsViewPlugin;
        this.extendsViewName = extendsViewName;
        this.extendsComponentName = extendsComponentName;
        this.columns = columns;
        this.viewDefinitionService = viewDefinitionService;
    }

    @Override
    public void enableOnStartup() {
        enable();
    }

    @Override
    public void enable() {
        InternalViewDefinition viewDefinition = viewDefinitionService.getWithoutSession(extendsViewPlugin, extendsViewName);
        if (viewDefinition == null) {
            throw new ModuleException(pluginIdentifier, "view", "reference to view which not exists");
        }
        ComponentPattern component = viewDefinition.getComponentByReference(extendsComponentName);
        if (component == null) {
            throw new ModuleException(pluginIdentifier, "view", "reference to component which not exists in " + extendsViewPlugin
                    + "/" + extendsViewName);

        }
        if (!(component instanceof GridComponentPattern)) {
            throw new ModuleException(pluginIdentifier, "view", "component '" + extendsComponentName + "' in "
                    + extendsViewPlugin + "/" + extendsViewName + " is not a grid");
        }
        GridComponentPattern grid = (GridComponentPattern) component;
        for (ViewGridColumnModuleColumnModel columnModel : columns) {
            grid.addColumn(columnModel.getName(), columnModel.getFields(), columnModel.getExpression(), columnModel.getLink(),
                    columnModel.getWidth(), columnModel.getOrderable(), columnModel.getSearchable());
        }
    }

    @Override
    public void disable() {

    }

}
