package com.xross.tools.xstate;

public interface EnterAction {
	void enter(String targetStateId, Event event);
}
