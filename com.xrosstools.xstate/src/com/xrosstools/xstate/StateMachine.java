package com.xrosstools.xstate;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;



public class StateMachine {
	private String name;
	private String description;
	private TransitionGuard gaurd;
	private State currentState;
	private State startState;
	private Map<String, State> stateMap;
	private Set<String> eventIds;
	
	public StateMachine(String name, String description, List<State> states, TransitionGuard gaurd) {
		this.name = name;
		this.description = description;
		this.gaurd = gaurd;
		
		init(states);
	}
	
	private void init(List<State> states) {
	    stateMap = new LinkedHashMap<String, State>();
        eventIds = new LinkedHashSet<>();
        Map<String, Set<String>> sourceEventMap = new HashMap<>();
	    
		for(State state: states) {
		    //Verify if there is multiple start states
			if(state.getType() == StateType.start)
				if(startState == null)
					startState = state;
				else
					throw new IllegalStateException("Found multiple start states. There should only one start state for a state machine.");
			
			//Check if there is duplicate state id
			if(stateMap.containsKey(state.getId()))
			    throw new IllegalStateException("Found duplicate state for Id: " + state.getId());
			
			stateMap.put(state.getId(), state);
			eventIds.addAll(state.getAcceptableEvents());
			
			//Locate all source event ids for each state
			for(String eid: state.getAcceptableEvents()) {
			    Transition tran = state.getTransition(new Event(eid));
			    String targetSid = tran.getTargetStateId();
			    Set<String> sourceEventIds = null;
			    if(sourceEventMap.containsKey(targetSid)) {
			        sourceEventIds = sourceEventMap.get(targetSid);
			    }else{
			        sourceEventIds = new LinkedHashSet<>();
			        sourceEventMap.put(targetSid, sourceEventIds);
			    }
			    sourceEventIds.add(eid);
			}
		}
		
		for(String targetSid: sourceEventMap.keySet()) {
		    State targetState = stateMap.get(targetSid);
		    if(sourceEventMap.containsKey(targetSid))
		        targetState.setSourceEvents(sourceEventMap.get(targetSid));
		    else
		        targetState.setSourceEvents(Collections.EMPTY_SET);
		}
		    
		currentState = startState;
	}
	
	public String getName(){
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<String> getEventIds() {
	    return new LinkedHashSet<>(eventIds);
	}
	
	public State getCurrentState(){
		return currentState;
	}
	
	public Set<String> getStateIds(){
        return new LinkedHashSet<>(stateMap.keySet());
    }
	
//	public void start() {
//		if(currentState != null)
//			throw new IllegalStateException(String.format("State machine: %s is already started. Currnet state id is %s", name, currentState.getId()));
//		
//		currentState = startState;
//	}
//	
	public State findState(String id) {
	    if(stateMap.containsKey(id))
	        return stateMap.get(id);
	    
		throw new NoSuchElementException(String.format("The given state id: %s is not found", id));
	}
	
	public boolean isStarted() {
		if(currentState == null)
			return false;
		
		return currentState.getType() != StateType.end;
	}
	
	public boolean notify(Event event){
		State source = currentState;
		
		if(!currentState.isAcceptable(event))
			return false;
		
		Transition trans = currentState.getTransition(event);
		State target = findState(trans.getTargetStateId());
		
		if(!gaurd.isTransitAllowed(source.getId(), target.getId(), event))
			return false;
		
		currentState.exist(event);
		trans.transit(event);
		target.enter(event);
		currentState = target;
		
		return true;
	}
	
	public boolean isEnded(){
		if(currentState == null)
			return false;
		
		return currentState.getType() == StateType.end;
	}
	
	/*
	 * Reset the state machine
	 */
	public void reset(){
		if(isEnded())
			throw new IllegalStateException(String.format("State machine: %s is already ended. Can not be reset.", name));
		
		currentState = startState;
	}
	
	/**
	 * Restore to given state
	 * @param id
	 */
	public void restore(String id){
		if(isEnded())
			throw new IllegalStateException(String.format("State machine: %s is already ended. Can not be restored.", name));

		currentState = findState(id);
	}
}
