package com.qcadoo.security.constants;

public enum PermissionType {

    SUPER_ADMIN("01superAdmin"), OFFICE_LICENSE("02officeLicense"), APS_LICENSE("03APSLicense"), WMS_MOBILE_LICENSE("04WMSMobileLicense"),
    TERMINAL_LICENSE("05terminalLicense"), LACK_OF_ACCESS("06lackOfAccess");

    private final String value;

    private PermissionType(final String type) {
        this.value = type;
    }

    public String getStringValue() {
        return value;
    }
}
