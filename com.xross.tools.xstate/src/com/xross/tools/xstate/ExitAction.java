package com.xross.tools.xstate;

public interface ExitAction {
	void exit(String sourceStateId, Event event);
}
