package com.xross.tools.xstate;

public class NullAction implements EntryAction, ExistAction, TransitionAction {

	public void transit(String transitionId, String sourceStateId,String targetStateId, Event event) {}

	public void exist(String sourceStateId, Event event) {}

	public void enter(String targetStateId, Event event) {}

}
