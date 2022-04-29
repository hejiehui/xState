package com.xrosstools.xstate.editor.commands;

public interface Accessor<T> {
    String name();
    T get();
    void set(T value);
}
