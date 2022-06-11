package com.qcadoo.security.constants;

public enum PermissionType {

    SUPER_ADMIN("01superAdmin"), OFFICE_LICENSE("02officeLicense"), TERMINAL_LICENSE("03terminalLicense"), LACK_OF_ACCESS("04lackOfAccess");

    private final String value;

    private PermissionType(final String type) {
        this.value = type;
    }

    public String getStringValue() {
        return value;
    }
}
