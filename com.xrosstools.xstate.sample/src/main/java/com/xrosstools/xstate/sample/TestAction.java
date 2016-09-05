package com.xrosstools.xstate.sample;

import com.xrosstools.xstate.EntryAction;
import com.xrosstools.xstate.Event;
import com.xrosstools.xstate.ExitAction;
import com.xrosstools.xstate.TransitAction;
import com.xrosstools.xstate.TransitionGuard;

public class TestAction implements EntryAction, ExitAction, TransitAction, TransitionGuard {
	public void transit(String sourceStateId,String targetStateId, Event event) {
		System.out.println(String.format("Transit from %s to %s on %s", sourceStateId, targetStateId, event.getId()));
	}

	public void exit(String sourceStateId, Event event) {
		System.out.println(String.format("%s -[%s]->", sourceStateId, event.getId()));
	}

	public void enter(String targetStateId, Event event) {
		System.out.println(String.format("-[%s]->%s", event.getId(), targetStateId));
	}

	public boolean isTransitAllowed(String sourceId, String targetId, Event event) {return true;}

}
