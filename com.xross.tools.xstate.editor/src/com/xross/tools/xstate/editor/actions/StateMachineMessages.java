package com.xross.tools.xstate.editor.actions;

public interface StateMachineMessages {
	String ALIGN_LEFT_MSG = "Align left";
	String ALIGN_CENTER_MSG = "Align center";
	String ALIGN_RIGHT_MSG = "Align right";
	
	String ALIGN_TOP_MSG = "Align top";
	String ALIGN_MIDDLE_MSG = "Align middle";
	String ALIGN_BOTTOM_MSG = "Align bottom";
	
	String INCREASE_NODE_HEIGHT_MSG = "Increase node height";
	String DECREASE_NODE_HEIGHT_MSG = "Decrease node height";
	
	String INCREASE_NODE_WIDTH_MSG = "Increase node width";
	String DECREASE_NODE_WIDTH_MSG = "Decrease node width";
	
	String INCREASE_VERTICAL_SPACE_MSG = "Increase vertical space between nods";
	String DECREASE_VERTICAL_SPACE_MSG = "Decrease vertical space between nods";
	
	String INCREASE_HORIZANTAL_SPACE_MSG = "Increase horizantal space between nods";
	String DECREASE_HORIZANTAL_SPACE_MSG = "Decrease horizantal space between nods";
	
	String SHOW_LABEL_INSIDE_MSG = "Show lable inside node";
	String SHOW_LABEL_OUTSIDE_MSG = "Show lable beside node";
	
	String GEN_TEST_CODE_MSG = "Generate stand alone test code";
	String GEN_JUNIT_TEST_CODE_MSG = "Generate junit test code";
	String CREATE_EVENT_MSG = "Create new Event";
	
	String CREATE_ENTRY_ACTION_MSG = "Create entry action";
	String CHANGE_ENTRY_ACTION_MSG = "Change entry action";
	String REMOVE_ENTRY_ACTION_MSG = "Remove entry action";
	String CREATE_EXIT_ACTION_MSG = "Create entry action";
	String CHANGE_EXIT_ACTION_MSG = "Change entry action";
	String REMOVE_EXIT_ACTION_MSG = "Remove entry action";
	String CREATE_TRANSIT_ACTION_MSG = "Create transit action";
	String CHANGE_TRANSIT_ACTION_MSG = "Change transit action";
	String REMOVE_TRANSIT_ACTION_MSG = "Remove transit action";

	String OPEN_TRANSIT_ACTION_MSG = "Open transit action";
	String OPEN_ENTRY_ACTION_MSG = "Open entry action";
	String OPEN_EXIT_ACTION_MSG = "Open exit action";


}
