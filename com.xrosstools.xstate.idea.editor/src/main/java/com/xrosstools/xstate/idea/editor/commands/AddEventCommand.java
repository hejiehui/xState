package com.xrosstools.xstate.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;

public class AddEventCommand extends Command {
    private StateMachine stateMachine;
    private Event event;
    
    public AddEventCommand(StateMachine diagram, Event event){
    	this.stateMachine = diagram;
    	this.event = event;
    }
    
    public void execute() {
    	stateMachine.addEvent(event);
    }

    public String getLabel() {
        return "Create new event";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	stateMachine.removeEvent(event);
    }
}
