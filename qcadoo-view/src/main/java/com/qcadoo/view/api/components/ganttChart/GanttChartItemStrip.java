package com.qcadoo.view.api.components.ganttChart;

import org.json.JSONException;
import org.json.JSONObject;

public interface GanttChartItemStrip {

    String getColor();

    int getSize();

    JSONObject getAsJson() throws JSONException;

    public enum Orientation {
        HORIZONTAL("horizontal"), VERTICAL("vertical");

        private final String stringValue;

        private Orientation(final String stringValue) {
            this.stringValue = stringValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public static Orientation parseString(final String string) {
            for (Orientation orientation : Orientation.values()) {
                if (orientation.getStringValue().equalsIgnoreCase(string)) {
                    return orientation;
                }
            }
            throw new IllegalArgumentException(String.format("Couldn't parse '%s'", string));
        }

    }

}
