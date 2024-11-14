package com.xrosstools.xstate.idea.editor.actions;

public interface StateMachineMessages {
	String CREATE_EVENT_MSG = "Create new Event";

	String ENTRY_MSG = "entry action";
	String EXIT_MSG = "exit action";
	String TRANSITION_MSG = "transition action";
	String TRANSITION_GUARD_MSG = "transition guard";

    String ON_EVENT_MSG = "On event ";
	String REFERENCE_MSG = "Refer to ";
    String REFERENCE_METHOD_MSG = "Refer to ";

    String ASSIGN_ACTION_MSG = "Assign %s";
    String CHANGE_ACTION_MSG = "Change %s";
    String REMOVE_ACTION_MSG = "Remove %s";
    String OPEN_ACTION_MSG = "Open %s";

    String MOVE_UP_MSG = "Move up %s";
    String MOVE_DOWN_MSG = "Move down %s";

    String GENERATE_FACTORY = "Generate factory";
    String GENERATE_TEST = "Generate test";
}
