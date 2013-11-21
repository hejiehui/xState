package com.xross.tools.xstate;

public interface TransitAction {
	void transit(String transitionId, String sourceStateId, String targetStateId, Event event);
}
