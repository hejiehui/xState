package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;

public class ChangeValueCommand<T> extends Command {
    private Accessor<T> accessor;
    private T oldValue;
    private T newValue;
    
    public ChangeValueCommand(Accessor<T> accessor, T newValue) {
        this.accessor= accessor;
        this.oldValue = accessor.get();
        this.newValue = newValue;
    }

    public String getLabel() {
        return "Change value";
    }
    
    public void execute() {
        accessor.set(newValue);
    }

    public void redo() {
        execute();
    }

    public void undo() {
        accessor.set(oldValue);
    }
}
