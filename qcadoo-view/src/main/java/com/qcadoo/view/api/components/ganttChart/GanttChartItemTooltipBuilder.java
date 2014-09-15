package com.qcadoo.view.api.components.ganttChart;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class GanttChartItemTooltipBuilder {

    private Optional<String> header = Optional.absent();

    private List<String> content = Lists.newArrayList();

    public GanttChartItemTooltip build() {
        return new GanttChartItemTooltip(header, content);
    }

    public GanttChartItemTooltipBuilder withHeader(final String header) {
        this.header = Optional.fromNullable(header);
        return this;
    }

    public GanttChartItemTooltipBuilder addLineToContent(final String contentLine) {
        Preconditions.checkArgument(contentLine != null);
        this.content.add(contentLine);
        return this;
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
        GanttChartItemTooltipBuilder rhs = (GanttChartItemTooltipBuilder) obj;
        return new EqualsBuilder().append(this.header, rhs.header).append(this.content, rhs.content).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(header).append(content).toHashCode();
    }
}
