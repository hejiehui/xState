package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;

public class ChangeMethodCommand extends Command {
    private Accessor accessor;
    private String oldValue;
    private String newValue;

    public ChangeMethodCommand(Accessor accessor, String newValue) {
        this.accessor= accessor;
        this.oldValue = accessor.getMethodName();
        this.newValue = newValue;
    }

    public String getLabel() {
        return "Change method";
    }

    public void execute() {
        accessor.setMethodName(newValue);
    }

    public void redo() {
        execute();
    }

    public void undo() {
        accessor.setMethodName(oldValue);
    }
}
