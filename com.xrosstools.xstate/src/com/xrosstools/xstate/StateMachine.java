package com.xrosstools.xstate;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;



public class StateMachine {
    public static final String STATE_ID_SEPARATOR = ".";
    private static final String SEPARATOR_REGEX = "\\.";
	private String name;
	private String description;
	private State currentState;
	private State startState;
	private Map<String, State> stateMap;
	private Set<String> eventIds;
	
	public StateMachine(String name, String description, List<State> states) {
		this.name = name;
		this.description = description;
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
	
    public String getCurrentStateId(){
        StateMachine child = currentState.getReference();
        
        if(child == null || child.isEnded())
            return currentState.getId();
        
        return currentState.getId() + STATE_ID_SEPARATOR + child.getCurrentStateId();
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
	
	public boolean notify(Event event) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        StateMachine child = currentState.getReference();
		if(child == null || child.isEnded())
		    return notifyState(event);

		return child.notify(event);
	}
	
	private boolean notifyState(Event event) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	    State source = currentState;

	    if(!currentState.isAcceptable(event))
	        return false;

	    Transition trans = currentState.getTransition(event);
	    State target = findState(trans.getTargetStateId());

	    if(!trans.isTransitAllowed(source.getId(), target.getId(), event))
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
	 * Reset the state machine when it is not ended
	 */
	public void reset(){
		if(isEnded())
			throw new IllegalStateException(String.format("State machine: %s is already ended. Can not be reset.", name));
		
		currentState = startState;
	}
	
    /*
     * Restart the state machine when it is ended
     */
    public void restart(){
        if(!isEnded())
            throw new IllegalStateException(String.format("State machine: %s is not ended. Can not be restarted.", name));
        
        currentState = startState;
    }
    
	/**
	 * Restore to given state
	 * @param id the ID of the given state of the state machine. 
	 * If the given state refers to a state machine and need to restore 
	 * the state in that machine, the ID needes to be concat with state Ids from both parent and child state machine.
	 * E.g. state S1 refers to state machine call SM1 which has a state called S2, if we want to retore S1 in S2 state,
	 * the ID should be S1.S2  
	 */
	public void restore(String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		if(isEnded())
			throw new IllegalStateException(String.format("State machine: %s is already ended. Can not be restored.", name));

		if(id.contains(STATE_ID_SEPARATOR)) {
		    String[] ids = id.split(SEPARATOR_REGEX, 2);
		    currentState = findState(ids[0]);
		    currentState.restore(ids[1]);
		    
		    return;
		}
		
		currentState = findState(id);
		
	}
}
