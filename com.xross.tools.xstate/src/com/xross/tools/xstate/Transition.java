package com.xross.tools.xstate;

public class Transition {
	private Event event;
	private TransitAction transitionAction;
	private State sourceState;
	private State targetState;
}
