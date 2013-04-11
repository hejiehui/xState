package com.xross.tools.xstate;

public interface TransitionAction {
	void transit(String transitionId, String sourceStateId, String targetStateId, Event event);
}
