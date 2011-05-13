package com.qcadoo.model.internal.hooks;

@SuppressWarnings("serial")
public class HookInitializationException extends Exception {

    private final String className;

    private final String methodName;

    public HookInitializationException(final String className, final String methodName, final String message) {
        super(message);
        this.className = className;
        this.methodName = methodName;
    }

    public HookInitializationException(final String className, final String methodName, final String message,
            final Throwable throwable) {
        super(message, throwable);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String getMessage() {
        return "Hook '" + className + "#" + methodName + "': " + super.getMessage();
    }

}
