package com.xrosstools.xstate.idea.editor.model;

import java.util.ArrayList;
import java.util.List;

public class StateMachineHelper implements StateMachineConstants {
	private StateMachine machine;
	
	public StateMachineHelper(StateMachine machine) {
		this.machine = machine;
	}
	
	public List<Event> getEvents() {
	    return machine.getEvents();
	}

	public String[] getEventIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(EMPTY_VALUE);
		for(Event e: machine.getEvents())
			ids.add(e.getDisplayText());
		return ids.toArray(new String[ids.size()]);
	}
	
	public int getEventIdIndex(Event event) {
		if(event == null)
			return 0;

		return machine.getEvents().indexOf(event) + 1;
	}
	
	public Event getEvent(int index) {
		if(index == 0)
			return null;
		
		return machine.getEvents().get(index - 1);
	}

	public String getValue(String value){
		return value == null? EMPTY_VALUE : value;
	}
}
