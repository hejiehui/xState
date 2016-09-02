package com.xrosstools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xstate.editor.model.Event;

public class ChangeEventNameCommand extends Command{
    private Event event;
    private String oldId;
    private String newId;
    
    public ChangeEventNameCommand(Event event, String newId){
    	this.event = event;
    	oldId = event.getId();
    	this.newId = newId;
    }
    
    public void execute() {
        event.setId(newId);
    }

    public String getLabel() {
        return "Change event name";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	event.setId(oldId);
    }
}
