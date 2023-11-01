package com.xrosstools.xstate;

public class NullAction implements EntryAction, ExitAction, TransitAction, TransitionGuard {
	public static final NullAction instance = new NullAction();
	
	public void transit(String sourceStateId,String targetStateId, Event event) {}

	public void exit(String sourceStateId, Event event) {}

	public void enter(String targetStateId, Event event) {}

	public boolean isTransitAllowed(String sourceId, String targetId, Event event) {return true;}
}
