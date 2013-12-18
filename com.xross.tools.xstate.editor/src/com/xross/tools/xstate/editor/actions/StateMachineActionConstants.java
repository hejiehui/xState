package com.xross.tools.xstate.editor.actions;

import com.xross.tools.xstate.editor.Activator;

public interface StateMachineActionConstants {
	String ID_PREFIX = Activator.PLUGIN_ID;
	String GEN_JUNIT_TEST_CODE = "generate_junit_test_code";

	String GEN_USAGE_CODE = "generate_usage_code";
	String CREATE_EVENT = "create_event";
	String CREATE_ENTRY_ACTION = "create_entry_action";
	String CHANGE_ENTRY_ACTION = "change_entry_action";
	String REMOVE_ENTRY_ACTION = "remove_entry_action";
	String CREATE_EXIT_ACTION = "create_entry_action";
	String CHANGE_EXIT_ACTION = "change_entry_action";
	String REMOVE_EXIT_ACTION = "remove_entry_action";
	String CREATE_TRANSIT_ACTION = "create_transit_action";
	String CHANGE_TRANSIT_ACTION = "change_transit_action";
	String REMOVE_TRANSIT_ACTION = "remove_transit_action";

	String OPEN_TRANSIT_ACTION = "open_transit_action";
	String OPEN_ENTRY_ACTION = "open_entry_action";
	String OPEN_EXIT_ACTION = "open_exit_action";


	
	String ALIGN_LEFT = "alignment_left";
	String ALIGN_CENTER = "alignment_center";
	String ALIGN_RIGHT = "alignment_right";

	String ALIGN_BOTTOM = "alignment_bottom";
	String ALIGN_MIDDLE = "alignment_middle";
	String ALIGN_TOP = "alignment_top";
	
	String INCREASE_NODE_HEIGHT = "increase_node_height";
	String DECREASE_NODE_HEIGHT = "decrease_node_height";
	
	String INCREASE_NODE_WIDTH = "increase_node_width";
	String DECREASE_NODE_WIDTH = "decrease_node_width";
	
	String INCREASE_VERTICAL_SPACE = "increase_vertical_space";
	String DECREASE_VERTICAL_SPACE = "decrease_vertical_space";
	
	String INCREASE_HORIZANTAL_SPACE = "increase_horizantal_space";
	String DECREASE_HORIZANTAL_SPACE = "decrease_horizantal_space";
	
	String SHOW_LABEL_INSIDE = "show_lable_inside";
	String SHOW_LABEL_OUTSIDE = "show_lable_outside";
}
