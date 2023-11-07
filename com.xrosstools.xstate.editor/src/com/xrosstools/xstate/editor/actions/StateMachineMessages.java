package com.xrosstools.xstate.editor.actions;

public interface StateMachineMessages {
	String SHOW_LABEL_INSIDE_MSG = "Show lable inside node";
	String SHOW_LABEL_OUTSIDE_MSG = "Show lable beside node";
	
	String GEN_TEST_CODE_MSG = "Generate stand alone test code";
	String GEN_JUNIT_TEST_CODE_MSG = "Generate junit test code";
	String CREATE_EVENT_MSG = "Create new Event";
	
	String ENTRY_MSG = "entry action";
	String EXIT_MSG = "exit action";
	String TRANSITION_MSG = "transition action";
	String TRANSITION_GUARD_MSG = "transition guard";
	String EVENT_MSG = "event";
	
	String ASSIGN_ACTION_MSG = "Assign %s";
	String CHANGE_ACTION_MSG = "Change %s";
	String REMOVE_ACTION_MSG = "Remove %s";
	String OPEN_ACTION_MSG = "Open %s";
}
