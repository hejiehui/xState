package com.xross.tools.xstate.editor.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.StateNode;

public class MoveNodeCommand extends Command {
    private StateNode node;

    private Rectangle oldConstraint;

    private Rectangle newConstraint;

    public void setConstraint(Rectangle c) {
    	newConstraint = c;
    }

    public void setNode(StateNode node) {
        this.node = node;
    }

    public void execute() {
    	oldConstraint = new Rectangle();
    	oldConstraint.setLocation(node.getLocation());
    	oldConstraint.setSize(node.getSize());
        node.setLocation(newConstraint.getLocation());
        node.setSize(newConstraint.getSize());
    }

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        node.setLocation(newConstraint.getLocation());
        node.setSize(newConstraint.getSize());
    }

    public void undo() {
        node.setLocation(oldConstraint.getLocation());
        node.setSize(oldConstraint.getSize());
    }
}
