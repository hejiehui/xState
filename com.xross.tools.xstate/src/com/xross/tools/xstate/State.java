package com.xross.tools.xstate;

import java.util.List;

public class State {
	private String name;
	private List<Transition> inputs;
	private List<Transition> outputs;
	private Action enterAction;
	private Action existAction;
}
