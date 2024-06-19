package com.xrosstools.xstate.idea.editor.commands;

public interface Accessor {
    String name();
    String get();
    void set(String value);

    default String getClassName() {
        String name = get();
        return name.contains(SEPARATOR) ? name.split(SEPARATOR)[0] : name;
    }
    default String getMethodName() {
        String name = get();
        return name.contains(SEPARATOR) ? name.split(SEPARATOR)[1] : DEFAULT_METHOD;
    }
    default void setClassName(String className) {
        set(className);
    }
    default void setMethodName(String methodName) {
        if(DEFAULT_METHOD.equals(methodName) || methodName == null || methodName.trim().length() == 0)
            set(get().split(SEPARATOR)[0]);
        else
            set(get().split(SEPARATOR)[0] + SEPARATOR + methodName);
    }

    String SEPARATOR = "::";
    String DEFAULT_METHOD = "#default";
}
