package com.xross.tools.xstate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
	private String id;
	private StateType type;
	private String description;
	private Map<String, Transition> outputs = new LinkedHashMap<String, Transition>();
	private EntryAction entryAction;
	private ExitAction existAction;
	
	public State(
			String id, 
			StateType type, 
			String description, 
			EntryAction entryAction, 
			ExitAction existAction, 
			List<Transition> transitions) {
		this.id = id;
		this.type = type;
		this.description = description;
		this.entryAction = NullAction.guardWith(entryAction);
		this.existAction = NullAction.guardWith(existAction);
		
		if(transitions == null)
			return;
		
		for(Transition trans: transitions) {
			if(outputs.containsKey(trans.getEventId()))
				throw new RuntimeException(String.format("Duplicate event: %s found for state: %s",  trans.getEventId(), id));
			outputs.put(trans.getEventId(), trans);
		}
	}

	public String getId() {
		return id;
	}
	
	public StateType getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}
	
	public Set<String> getAcceptableEvents() {
		return outputs.keySet();
	}
	
	public boolean isAcceptable(Event event) {
		return outputs.containsKey(event.getId());
	}
	
	public Transition getTransition(Event event) {
		return outputs.get(event.getId());
	}

	public void enter(Event event) {
		entryAction.enter(id, event);
	}

	public void exist(Event event) {
		existAction.exit(id, event);
	}
}
