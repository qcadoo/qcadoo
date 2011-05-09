package com.qcadoo.model.internal.types;

public final class EnumTypeKey {

    private final String value;

    private final String originPluginIdentifier;

    public EnumTypeKey(final String value, final String originPluginIdentifier) {
        this.value = value;
        this.originPluginIdentifier = originPluginIdentifier;
    }

    public String getValue() {
        return value;
    }

    public String getOriginPluginIdentifier() {
        return originPluginIdentifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((originPluginIdentifier == null) ? 0 : originPluginIdentifier.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EnumTypeKey other = (EnumTypeKey) obj;
        if (originPluginIdentifier == null) {
            if (other.originPluginIdentifier != null) {
                return false;
            }
        } else if (!originPluginIdentifier.equals(other.originPluginIdentifier)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EnumTypeKey [value=" + value + ", originPluginIdentifier=" + originPluginIdentifier + "]";
    }

}
