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

	
	public State getCurrentState(){
		return null;
	}
	
	public List<Event> getAcceptableEvent(){
		return null;
	}
	
	public State notify(Event event){
		
		return null;
	}
	
	public boolean isEnd(){
		return false;
	}
}
