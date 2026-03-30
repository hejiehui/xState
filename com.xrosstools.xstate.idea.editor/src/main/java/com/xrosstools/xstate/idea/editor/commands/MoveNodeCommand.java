package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.StateNode;

import java.awt.*;

public class MoveNodeCommand extends Command {
    private StateNode node;

    private Point newLocation;
    private Point oldLocation;

    public void setMoveDelta(Point delta) {
        oldLocation = node.getLocation();
        newLocation = delta;
    }

    public void setNode(StateNode node) {
        this.node = node;
    }

    public void execute() {
        node.setLocation(newLocation);
    }

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        node.setLocation(newLocation);
    }

    public void undo() {
        node.setLocation(oldLocation);
    }
}
