package com.xross.tools.xstate;

public class Transition {
	private String eventId;
	private TransitAction transitionAction;
	private State sourceState;
	private State targetState;
	
	public Transition(
			String eventId,
			TransitAction transitionAction,
			State sourceState,
			State targetState) {
		this.eventId = eventId;
		this.transitionAction = transitionAction == null? NullAction.nullAction : transitionAction;
		this.sourceState = sourceState;
		this.targetState = targetState;
	}
	
	public String getEventId() {
		return eventId;
	}
	public void transit(Event event) {
		transitionAction.transit(sourceState.getId(), targetState.getId(), event);
	}
	
	public State getSourceState() {
		return sourceState;
	}
	
	public State getTargetState() {
		return targetState;
	}
}
