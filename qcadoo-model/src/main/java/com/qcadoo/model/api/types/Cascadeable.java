package com.qcadoo.model.api.types;

import org.apache.commons.lang3.StringUtils;

public interface Cascadeable {

    /**
     * Cascade type.
     */
    enum Cascade {
        NULLIFY("nullify"), DELETE("delete");

        private final String stringValue;

        private Cascade(final String stringValue) {
            this.stringValue = stringValue;
        }

        public static Cascade parse(final String stringValue) {
            if (StringUtils.isBlank(stringValue)) {
                return NULLIFY;
            }
            for (final Cascade cascadeType : Cascade.values()) {
                if (cascadeType.getStringValue().equalsIgnoreCase(stringValue)) {
                    return cascadeType;
                }
            }
            throw new IllegalArgumentException(String.format("Illegal 'cascade' attribute value '%s'.", stringValue));
        }

        public String getStringValue() {
            return stringValue;
        }
    }

    /**
     * Returns cascade type.
     * 
     * @return cascade type
     */
    Cascade getCascade();

}
