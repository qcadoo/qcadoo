package com.qcadoo.view.internal.xml;

public class ViewDefinitionParserException extends RuntimeException {

    private static final long serialVersionUID = -7437167697213640121L;

    private final String fileName;

    private final String node;

    public static ViewDefinitionParserException forFile(final String fileName, final Throwable cause) {
        return new ViewDefinitionParserException(fileName, cause.getMessage(), cause, null);
    }

    public static ViewDefinitionParserException forFile(final String fileName, final String message, final Throwable cause) {
        return new ViewDefinitionParserException(fileName, message, cause, null);
    }

    public static ViewDefinitionParserException forFileAndNode(final String fileName,
            final ViewDefinitionParserNodeException cause) {
        return new ViewDefinitionParserException(fileName, cause.getOriginalMessage(), cause, cause.getNode());
    }

    private ViewDefinitionParserException(final String fileName, final String message, final Throwable cause, final String node) {
        super(message, cause);
        this.fileName = fileName;
        this.node = node;
    }

    @Override
    public String getMessage() {
        if (node == null) {
            return "Error while parsing view file '" + fileName + "': " + super.getMessage();
        } else {
            return "Error while parsing view file '" + fileName + "' in node " + node + ": " + super.getMessage();
        }
    }
}
