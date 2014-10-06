package com.qcadoo.view.api.components.ganttChart;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Optional;

public final class GanttChartItemTooltip {

    private final Optional<String> header;

    private final List<String> content;

    GanttChartItemTooltip(final Optional<String> header, final List<String> content) {
        this.header = header;
        this.content = content;
    }

    public Optional<String> getHeader() {
        return header;
    }

    public List<String> getContent() {
        return content;
    }

    public JSONObject getAsJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("header", header.orNull());
        json.put("content", new JSONArray(content));
        return json;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        GanttChartItemTooltip rhs = (GanttChartItemTooltip) obj;
        return new EqualsBuilder().append(this.header, rhs.header).append(this.content, rhs.content).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(header).append(content).toHashCode();
    }
}
