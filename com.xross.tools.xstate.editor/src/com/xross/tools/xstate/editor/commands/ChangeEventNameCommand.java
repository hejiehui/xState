package com.xross.tools.xstate.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xstate.editor.model.Event;

public class ChangeEventNameCommand extends Command{
    private Event event;
    private String oldName;
    private String newName;
    
    public ChangeEventNameCommand(Event event, String newName){
    	this.event = event;
    	oldName = event.getName();
    	this.newName = newName;
    }
    
    public void execute() {
        event.setName(newName);
    }

    public String getLabel() {
        return "Change event name";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	event.setName(oldName);
    }
}
