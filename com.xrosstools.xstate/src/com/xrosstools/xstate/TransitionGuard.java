package com.xrosstools.xstate;

public interface TransitionGuard {
	boolean isTransitAllowed(String sourceId, String targetId, Event event);
}
