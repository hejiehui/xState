package com.xrosstools.xstate.idea.editor.actions;

public interface StateMachineMessages {
	String SHOW_LABEL_INSIDE_MSG = "Show lable inside node";
	String SHOW_LABEL_OUTSIDE_MSG = "Show lable beside node";
	
	String GEN_TEST_CODE_MSG = "Generate stand alone test code";
	String GEN_JUNIT_TEST_CODE_MSG = "Generate junit test code";
	String CREATE_EVENT_MSG = "Create new Event";
	
	String ENTRY_MSG = "entry";
	String EXIT_MSG = "exit";
	String TRANSITION_MSG = "transition";
	
	String ASSIGN_ACTION_MSG = "Assign %s action";
	String CHANGE_ACTION_MSG = "Change %s action";
	String REMOVE_ACTION_MSG = "Remove %s action";
	String OPEN_ACTION_MSG = "Open %s action";
}
