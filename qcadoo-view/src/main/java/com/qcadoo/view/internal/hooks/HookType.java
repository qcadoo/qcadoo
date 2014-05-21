package com.qcadoo.view.internal.hooks;

import org.apache.commons.lang3.StringUtils;

public enum HookType {
    AFTER_INITIALIZE("afterInitialize", Category.LIFECYCLE_HOOK) {
    },
    BEFORE_RENDER("beforeRender", Category.LIFECYCLE_HOOK) {
    },
    BEFORE_INITIALIZE("beforeInitialize", Category.LIFECYCLE_HOOK) {
    },
    POST_CONSTRUCT("postConstruct", Category.CONSTRUCTION_HOOK) {
    },
    LISTENER("listener", Category.EVENT_LISTENER) {
    };

    public static enum Category {
        EVENT_LISTENER, LIFECYCLE_HOOK, CONSTRUCTION_HOOK;
    }

    private final String nodeName;

    private final Category category;

    private HookType(final String nodeName, final Category category) {
        this.nodeName = nodeName;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public static HookType parseString(final String nodeName) {
        for (HookType hookType : values()) {
            if (StringUtils.equalsIgnoreCase(nodeName, hookType.nodeName)) {
                return hookType;
            }
        }
        throw new IllegalArgumentException(String.format("Unknown hook type: %s", nodeName));
    }

}
