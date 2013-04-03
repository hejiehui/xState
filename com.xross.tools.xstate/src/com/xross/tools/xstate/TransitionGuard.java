package com.xross.tools.xstate;

public interface TransitionGuard {
	boolean isTransitAllowed(State source, State target, Event event);
}
