package com.xross.tools.xstate;

import java.util.List;

public class State {
	private String id;
	private List<Transition> inputs;
	private List<Transition> outputs;
	private EntryAction entryAction;
	private ExitAction existAction;
}
