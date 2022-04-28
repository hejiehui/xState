package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateTransition;

public class SelectEventCommand extends Command{
    private StateTransition transition;
    private Event oldEvent;
    private Event newEvent;
    
    
    public SelectEventCommand(StateTransition transition, Event newEvent) {
        this.transition = transition;
        this.oldEvent = transition.getEvent();
        this.newEvent = newEvent;
    }

    public void execute() {
        transition.setEvent(newEvent);
    }

    public String getLabel() {
        return "Change event name";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        transition.setEvent(oldEvent);
    }
}
