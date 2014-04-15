package com.qcadoo.view.internal.components.grid;

public class GridComponentOrderColumn {

    private String name;

    private String direction;

    public GridComponentOrderColumn(final String name, final String direction) {
        super();
        this.name = name;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

}
