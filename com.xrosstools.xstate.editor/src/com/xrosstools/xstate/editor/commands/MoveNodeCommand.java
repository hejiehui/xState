package com.xrosstools.xstate.editor.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.StateNode;

public class MoveNodeCommand extends Command {
    private StateNode node;

    private Point newLocation;
    private Point oldLocation;

    public void setMoveDelta(Point delta) {
        oldLocation = node.getLocation();

        int x = node.getLocation().x + delta.x;
        int y = node.getLocation().y + delta.y;
        
        newLocation = new Point(x, y);
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
