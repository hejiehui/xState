package com.xross.tools.xstate.def;

public class TransitionDef {
	private EventDef eventDef;
	private ActionDef transitActionDef;
	private StateDef source;
	private StateDef target;
	
	public TransitionDef(StateDef source, StateDef target) {
		this.source = source;
		this.target = target;
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
	public StateDef getSource() {
		return source;
	}
	public void setSource(StateDef source) {
		this.source = source;
	}
	public StateDef getTarget() {
		return target;
	}
	public void setTarget(StateDef target) {
		this.target = target;
	}
	
}
