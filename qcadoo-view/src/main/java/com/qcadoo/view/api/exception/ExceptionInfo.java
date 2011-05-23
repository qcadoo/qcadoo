package com.qcadoo.view.api.exception;

public class ExceptionInfo {

    private final String messageHeader;

    private final String messageExplanation;

    private final String[] messageExplanationArgs;

    public ExceptionInfo(final String messageHeader, final String messageExplanation, final String... messageExplanationArgs) {
        this.messageHeader = messageHeader;
        this.messageExplanation = messageExplanation;
        this.messageExplanationArgs = messageExplanationArgs;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public String getMessageExplanation() {
        return messageExplanation;
    }

    public String[] getMessageExplanationArgs() {
        return messageExplanationArgs;
    }

}
