package com.qcadoo.view.internal.xml;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ViewDefinitionParserNodeException extends Exception {

    private static final long serialVersionUID = -275974470599759775L;

    private final Node node;

    public ViewDefinitionParserNodeException(final Node node, final String message) {
        super(message);
        this.node = node;
    }

    public ViewDefinitionParserNodeException(final Node node, final Throwable cause) {
        super(cause.getMessage(), cause);
        this.node = node;
    }

    @Override
    public String getMessage() {
        return "Error while parsing node " + getNode() + ": " + super.getMessage();
    }

    public String getOriginalMessage() {
        return super.getMessage();
    }

    public String getNode() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        addNodePath(builder);
        builder.append(": ");
        addNodeHeader(builder);
        builder.append("]");
        return builder.toString();
    }

    private void addNodeHeader(final StringBuilder builder) {
        builder.append("<");
        builder.append(node.getNodeName());
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attributeNode = attributes.item(i);
                builder.append(" ");
                builder.append(attributeNode.getNodeName());
                builder.append("=\"");
                builder.append(attributeNode.getTextContent());
                builder.append("\"");
            }
        }
        builder.append(">");
    }

    private void addNodePath(final StringBuilder builder) {
        List<String> nodePath = new LinkedList<String>();
        Node parent = node;
        while (Node.ELEMENT_NODE == parent.getNodeType()) {
            nodePath.add(parent.getNodeName());
            parent = parent.getParentNode();
        }
        nodePath.add("#root");
        Collections.reverse(nodePath);
        for (String nodeName : nodePath) {
            builder.append(nodeName);
            builder.append("/");
        }
        builder.deleteCharAt(builder.length() - 1);
    }
}
