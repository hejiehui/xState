package com.xrosstools.xstate;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xrosstools.xstate.def.StateMachineDef;

public class State {
	private String id;

	private String referenceId;
	private StateMachine reference;
	private StateMachineFactory factory;

	private StateType type;
	private String description;
	private Map<String, Transition> outputs = new LinkedHashMap<String, Transition>();
	private Set<String> sourceEvents = new HashSet<String>();
	private EntryAction entryAction;
	private ExitAction existAction;
	
	public State(
			String id, 
            String referenceId,
            StateMachineFactory factory,
			StateType type, 
			String description, 
			EntryAction entryAction, 
			ExitAction existAction, 
			List<Transition> transitions) {
		this.id = id;
        this.referenceId = referenceId;
        this.factory = factory;
		this.type = type;
		this.description = description;
		this.entryAction = entryAction;
		this.existAction = existAction;
		
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
	
	public StateMachine getReference() {
		return reference;
	}

	public Set<String> getAcceptableEvents() {
		return new LinkedHashSet<>(outputs.keySet());
	}
	
    public Set<String> getSourceEvents() {
        return new LinkedHashSet<>(sourceEvents);
    }
    
	void setSourceEvents(Set<String> sourceEvents) {
        this.sourceEvents = sourceEvents;
    }

    public boolean isAcceptable(Event event) {
		return outputs.containsKey(event.getId());
	}
	
	public Transition getTransition(Event event) {
		return outputs.get(event.getId());
	}

	public void enter(Event event) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		entryAction.enter(id, event);
		
		if(referenceId == null || referenceId.isEmpty())
		    return;

		resetReference();
	}
	
	public void restore(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	    resetReference();
	    reference.restore(id);
	}
	
	private void resetReference() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if(reference == null)
            reference = factory.create(referenceId);

        if(reference.isEnded())
            reference.restart();
	}

	public void exist(Event event) {
		existAction.exit(id, event);
	}
}
