package com.xrosstools.xstate;

public class Transition {
	private String eventId;
	private TransitionGuard guard;
	private TransitAction transitionAction;
	private String sourceStateId;
	private String targetStateId;
	
	public Transition(
			String eventId,
			TransitionGuard guard,
			TransitAction transitionAction,
			String sourceStateId,
			String targetStateId) {
		this.eventId = eventId;
		this.guard = guard;
		this.transitionAction = transitionAction;
		this.sourceStateId = sourceStateId;
		this.targetStateId = targetStateId;
	}
	
	public String getEventId() {
		return eventId;
	}

	public boolean isTransitAllowed(String sourceId, String targetId, Event event) {
	    return guard.isTransitAllowed(sourceId, targetId, event);
	}
	public void transit(Event event) {
		transitionAction.transit(sourceStateId, targetStateId, event);
	}
	
	public String getSourceStateId() {
		return sourceStateId;
	}
	
	public String getTargetStateId() {
		return targetStateId;
	}
}
