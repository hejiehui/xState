package com.xross.tools.xstate.editor.model;

public class StateTransition {
	private Event event;
	private String transitAction;
	private StateNode source;
	private StateNode target;
	
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public String getTransitAction() {
		return transitAction;
	}
	public void setTransitAction(String transitAction) {
		this.transitAction = transitAction;
	}
	public StateNode getSource() {
		return source;
	}
	public void setSource(StateNode source) {
		this.source = source;
	}
	public StateNode getTarget() {
		return target;
	}
	public void setTarget(StateNode target) {
		this.target = target;
	}
	
}
