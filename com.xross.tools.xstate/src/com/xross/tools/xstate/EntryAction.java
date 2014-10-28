package com.xross.tools.xstate;

/**
 * Action triggered when entering a state
 * @author Jerry He
 */
public interface EntryAction {
	void enter(String targetStateId, Event event);
}
