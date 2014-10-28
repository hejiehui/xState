package com.xross.tools.xstate;

public class NullAction implements EntryAction, ExitAction, TransitAction {
	public static final NullAction nullAction = new NullAction();
	
	public void transit(String sourceStateId,String targetStateId, Event event) {}

	public void exit(String sourceStateId, Event event) {}

	public void enter(String targetStateId, Event event) {}
}
