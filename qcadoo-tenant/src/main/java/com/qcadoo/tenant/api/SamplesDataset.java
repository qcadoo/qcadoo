package com.qcadoo.tenant.api;

import org.apache.commons.lang.StringUtils;

/**
 * Represents sample data set generation strategies
 * 
 * @since 1.1.6
 */
public enum SamplesDataset {

    /**
     * Do not generate any samples data, leave database untouched
     */
    NONE("none"),

    /**
     * Generate minimal samples data
     */
    MINIMAL("minimal"),

    /**
     * Generate test samples data
     */
    TEST("test"),

    /**
     * Generate randomized samples data
     */
    GENERATED("generated");

    private String stringValue;

    private SamplesDataset(final String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Getter for string representation
     * 
     * @return string representation
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Parse {@link SamplesDataset} from string
     * 
     * @param string
     *            to parse
     * @return appropriate {@link SamplesDataset}
     * @throws IllegalArgumentException
     *             if there is no {@link SamplesDataset} matching to given string
     */
    public static final SamplesDataset parseString(final String string) {
        if (StringUtils.isBlank(string) || "none".equalsIgnoreCase(string)) {
            return NONE;
        } else if ("minimal".equalsIgnoreCase(string)) {
            return MINIMAL;
        } else if ("test".equalsIgnoreCase(string)) {
            return TEST;
        } else if ("generated".equalsIgnoreCase(string)) {
            return GENERATED;
        }
        throw new IllegalArgumentException("Wrong samples dataset: " + string);
    }
}
