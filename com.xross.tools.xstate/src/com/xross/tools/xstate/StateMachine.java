package com.xross.tools.xstate;

import java.util.List;

public class StateMachine {
	private String name;
	private TransitionGuard gaurd;
	
	StateMachine(String name){
		
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
}
