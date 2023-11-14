package com.xrosstools.xstate;

public class PrintTrailAction implements EntryAction, ExitAction, TransitAction, TransitionGuard {
	public static final PrintTrailAction instance = new PrintTrailAction();
	
	public void transit(String sourceStateId,String targetStateId, Event event) {
		System.out.println(String.format("Transit from \"%s\" to \"%s\" on \"%s\"", sourceStateId, targetStateId, event.getId()));
	}

	public void exit(String sourceStateId, Event event) {
		System.out.println(String.format("Exit from \"%s\" on \"%s\"", sourceStateId, event.getId()));
	}

	public void enter(String targetStateId, Event event) {
		System.out.println(String.format("Enter into \"%s\" on \"%s\"", targetStateId, event.getId()));
	}

	public boolean isTransitAllowed(String sourceStateId, String targetStateId, Event event) {
	    System.out.println(String.format("Transit from \"%s\" to \"%s\" on \"%s\" is allowed", sourceStateId, targetStateId, event.getId()));
	    return true;
    }
}
