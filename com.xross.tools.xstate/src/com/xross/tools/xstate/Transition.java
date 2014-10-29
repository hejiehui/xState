package com.xross.tools.xstate;

public class Transition {
	private String eventId;
	private TransitAction transitionAction;
	private String sourceStateId;
	private String targetStateId;
	
	public Transition(
			String eventId,
			TransitAction transitionAction,
			String sourceStateId,
			String targetStateId) {
		this.eventId = eventId;
		this.transitionAction = NullAction.guardWith(transitionAction);
		this.sourceStateId = sourceStateId;
		this.targetStateId = targetStateId;
	}
	
	public String getEventId() {
		return eventId;
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
