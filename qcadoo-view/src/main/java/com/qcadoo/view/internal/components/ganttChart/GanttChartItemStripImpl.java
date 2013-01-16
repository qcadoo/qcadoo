package com.qcadoo.view.internal.components.ganttChart;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.components.ganttChart.GanttChartItemStrip;

public class GanttChartItemStripImpl implements GanttChartItemStrip {

    private final String color;

    private final int size;

    public GanttChartItemStripImpl(final String cssColor, final int size) {
        this.color = cssColor;
        this.size = size;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public JSONObject getAsJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("color", color);
        json.put("size", size);
        return json;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1, 31).append(color).append(size).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GanttChartItemStripImpl other = (GanttChartItemStripImpl) obj;
        return new EqualsBuilder().append(color, other.color).append(size, other.size).isEquals();
    }

}
