package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.xstate.idea.editor.platform.ReferenceUtil;

import static com.xrosstools.xstate.idea.editor.platform.ReferenceUtil.*;

public interface Accessor {
    String name();
    String get();
    void set(String value);

    default String getClassName() {
        return ReferenceUtil.getClassName(get());
    }
    default String getMethodName() {
        return ReferenceUtil.getMethodName(get());
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
}
