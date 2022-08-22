package com.xrosstools.xstate.idea.editor.commands;

public interface Accessor<T> {
    String name();
    T get();
    void set(T value);
}
