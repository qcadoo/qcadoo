package com.qcadoo.report.api;

import java.util.Arrays;

public class ReportException extends Exception {

    public static enum Type {
        JSON_EXCEPTION("jsonException"), WRONG_REPORT_TYPE("wrongType"), ERROR_WHILE_COPYING_REPORT_TO_RESPONSE(
                "errorWhileCopyingToResponse"), NO_TEMPLATE_FOUND("noTemplateFound"), GENERATE_REPORT_EXCEPTION(
                "generateReportException");

        private String code;

        Type(final String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    private final Type type;

    private final String[] args;

    private static final long serialVersionUID = -4187117804067638882L;

    public ReportException(final Type type, final String... args) {
        this(type, null, args);
    }

    public ReportException(final Type type, final Throwable cause, final String... args) {
        super(ReportException.generateMessage(type, args), cause);
        this.type = type;
        this.args = args;
    }

    private static String generateMessage(final Type type, final String... args) {
        if (args.length == 0) {
            return type.getCode();
        } else {
            return type.getCode() + ": " + Arrays.toString(args);
        }
    }

    public String getCode() {
        return type.getCode();
    }

    public String[] getArgs() {
        return args;
    }
}
