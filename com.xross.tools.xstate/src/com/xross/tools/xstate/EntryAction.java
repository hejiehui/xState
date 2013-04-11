package com.xross.tools.xstate;

public interface EntryAction {
	void enter(String targetStateId, Event event);
}
