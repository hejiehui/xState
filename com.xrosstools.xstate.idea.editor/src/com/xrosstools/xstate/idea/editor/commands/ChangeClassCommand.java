package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;

public class ChangeClassCommand extends Command {
    private Accessor accessor;
    private String oldValue;
    private String newValue;
    
    public ChangeClassCommand(Accessor accessor, String newValue) {
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
