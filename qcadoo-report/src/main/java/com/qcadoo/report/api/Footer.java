package com.qcadoo.report.api;

/**
 * Footer definition object.
 * 
 * @author krzysztofnadolski
 * 
 */
public class Footer {

    private final String page;

    private final String in;

    private final String companyName;

    private final String address;

    private final String phoneEmail;

    private final String generatedBy;

    private final String additionalText;

    public Footer(String page, String in, String companyName, String address, String phoneEmail, String generatedBy,
            String additionalText) {
        this.page = page;
        this.in = in;
        this.companyName = companyName;
        this.address = address;
        this.phoneEmail = phoneEmail;
        this.generatedBy = generatedBy;
        this.additionalText = additionalText;
    }

    public String getPage() {
        return page;
    }

    public String getIn() {
        return in;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneEmail() {
        return phoneEmail;
    }

}
