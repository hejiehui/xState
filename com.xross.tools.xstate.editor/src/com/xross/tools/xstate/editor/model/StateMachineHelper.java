package com.xross.tools.xstate.editor.model;

import java.util.ArrayList;
import java.util.List;

public class StateMachineHelper implements StateMachineConstants {
	private StateMachine machine;
	
	public StateMachineHelper(StateMachine machine) {
		this.machine = machine;
	}
	
	public String[] getEventIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(EMPTY_VALUE);
		for(Event e: machine.getEvents())
			ids.add(e.getId());
		return ids.toArray(new String[ids.size()]);
	}
	
	public int getEventIdIndex(Event event) {
		if(event == null)
			return 0;
		
		String value = getValue(event.getId());

		String[] ids = getEventIds();
		for(int i = 0; i< ids.length; i++)
			if(value.equals(ids[i]))
				return i;
		return 0;
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
