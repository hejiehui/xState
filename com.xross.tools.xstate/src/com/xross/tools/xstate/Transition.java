package com.xross.tools.xstate;

public class Transition {
	private String id;
	private TransitAction transitionAction;
	private State sourceState;
	private State targetState;
}
