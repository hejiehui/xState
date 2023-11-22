package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateNode;

public class SelectReferenceCommand extends Command {
    private StateNode node;
    private String oldReference;
    private String newReference;


    public SelectReferenceCommand(StateNode node, String newReference) {
        this.node = node;
        this.oldReference = node.getReference();
        this.newReference = newReference;
    }

    public void execute() {
        node.setReference(newReference);
    }

    public String getLabel() {
        return "Change state node reference";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        node.setReference(oldReference);
    }
}