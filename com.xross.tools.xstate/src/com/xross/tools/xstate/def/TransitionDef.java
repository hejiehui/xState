package com.xross.tools.xstate.def;

public class TransitionDef {
	private EventDef eventDef;
	private ActionDef transitActionDef;
	private String sourceId;
	private String targetId;
	
	public TransitionDef(String sourceId, String targetId) {
		this.sourceId = sourceId;
		this.targetId = targetId;
	}
	
	public ActionDef getTransitActionDef() {
		return transitActionDef;
	}
	public void setTransitActionDef(ActionDef transitActionDef) {
		this.transitActionDef = transitActionDef;
	}
	public EventDef getEventDef() {
		return eventDef;
	}
	public void setEventDef(EventDef eventDef) {
		this.eventDef = eventDef;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
