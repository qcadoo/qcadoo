package com.qcadoo.view.internal.components.grid;

public class GridComponentMultiSearchFilterRule {

    public static final String JSON_FIELD_FIELD = "field";

    public static final String JSON_OPERATOR_FIELD = "op";

    public static final String JSON_DATA_FIELD = "data";

    private String field;

    private GridComponentFilterOperator filterOperator;

    private String data;

    public GridComponentMultiSearchFilterRule(String field, GridComponentFilterOperator filterOperator, String data) {
        this.setField(field);
        this.setFilterOperator(filterOperator);
        this.setData(data);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public GridComponentFilterOperator getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(GridComponentFilterOperator filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
