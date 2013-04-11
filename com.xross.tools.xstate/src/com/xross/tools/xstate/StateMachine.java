package com.xross.tools.xstate;

import java.util.List;

//TODO Do we need variable?
public class StateMachine {
	private String name;
	private TransitionGuard gaurd;
	//? do we need it?
	private StateMachineContext context;
	
	StateMachine(String name){
		
	}
	
	StateMachine(String name, StateMachineContext context){
		
	}
	
	public String getName(){
		return name;
	}

	
	public String getCurrentStateName(){
		return null;
	}
	
	public List<Event> getAcceptableEvents(){
		return null;
	}
	
	public List<String> getNextStateNames(){
		return null;
	}
	
	public boolean notify(Event event){
		
		return true;
	}
	
	public boolean isEnd(){
		return false;
	}
	
	/*
	 * Reset the state machine
	 */
	public void reset(){
		
	}
	
	public void restore(String stateId){
		
	}
}
