package com.qcadoo.view.internal.module.gridColumn;

public class ViewGridColumnModuleColumnModel {

    private final String name;

    private final String fields;

    private String expression;

    private Integer width;

    private boolean link = false;

    private boolean searchable = false;

    private boolean orderable = false;

    public ViewGridColumnModuleColumnModel(final String name, final String fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean getLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public boolean getOrderable() {
        return orderable;
    }

    public void setOrderable(Boolean orderable) {
        this.orderable = orderable;
    }

    public String getName() {
        return name;
    }

    public String getFields() {
        return fields;
    }

}
