package com.qcadoo.view.internal.module.gridColumn;

import java.util.LinkedList;
import java.util.List;

import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.qcadoo.plugin.api.ModuleFactory;
import com.qcadoo.view.internal.api.InternalViewDefinitionService;

public class ViewGridColumnModuleFactory extends ModuleFactory<ViewGridColumnModule> {

    @Autowired
    private InternalViewDefinitionService viewDefinitionService;

    @Override
    protected ViewGridColumnModule parseElement(String pluginIdentifier, Element element) {

        String plugin = getRequiredAttribute(element, "plugin");
        String view = getRequiredAttribute(element, "view");
        String component = getRequiredAttribute(element, "component");
        List<ViewGridColumnModuleColumnModel> columns = new LinkedList<ViewGridColumnModuleColumnModel>();
        @SuppressWarnings("unchecked")
        List<Element> children = (List<Element>) element.getChildren();

        for (Element columnElement : children) {
            String columnName = getRequiredAttribute(columnElement, "name");
            String columnFields = getRequiredAttribute(columnElement, "fields");
            String columnExpression = getAttribute(columnElement, "expression");
            String columnLink = getAttribute(columnElement, "link");
            String columnWidth = getAttribute(columnElement, "width");
            String columnSearchable = getAttribute(columnElement, "searchable");
            String columnOrderable = getAttribute(columnElement, "orderable");

            ViewGridColumnModuleColumnModel columnModel = new ViewGridColumnModuleColumnModel(columnName, columnFields);
            columnModel.setExpression(columnExpression);
            if (columnLink != null) {
                columnModel.setLink(Boolean.parseBoolean(columnLink));
            }
            if (columnWidth != null) {
                columnModel.setWidth(Integer.parseInt(columnWidth));
            }
            if (columnSearchable != null) {
                columnModel.setSearchable(Boolean.parseBoolean(columnSearchable));
            }
            if (columnOrderable != null) {
                columnModel.setOrderable(Boolean.parseBoolean(columnOrderable));
            }

            columns.add(columnModel);
        }

        return new ViewGridColumnModule(pluginIdentifier, plugin, view, component, columns, viewDefinitionService);
    }

    @Override
    public String getIdentifier() {
        return "view-grid-column";
    }

}
