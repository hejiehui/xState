package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface StateMachineIcons {
    Icon STATE_MACHINE_DIAGRAM = IconLoader.getIcon("/icons/state_machine_diagram.png", StateMachineIcons.class);
    Icon STATE_MACHINE = IconLoader.getIcon("/icons/state_machine.png", StateMachineIcons.class);
    Icon STATE_NODE = IconLoader.getIcon("/icons/start_node.png", StateMachineIcons.class);
    Icon START_NODE = IconLoader.getIcon("/icons/start_node.png", StateMachineIcons.class);
    Icon END_NODE = IconLoader.getIcon("/icons/end_node.png", StateMachineIcons.class);
    Icon EVENT = IconLoader.getIcon("/icons/event.png", StateMachineIcons.class);
    Icon STATE_TRANSITION = IconLoader.getIcon("/icons/transition.png", StateMachineIcons.class);
    Icon ROUTE_DIRECT = IconLoader.getIcon("/icons/arrows_direct.png", StateMachineIcons.class);
    Icon ROUTE_HEIGHT_FIRST = IconLoader.getIcon("/icons/arrows_height.png", StateMachineIcons.class);
    Icon ROUTE_WIDTH_FIRST = IconLoader.getIcon("/icons/arrows_width.png", StateMachineIcons.class);
    Icon ENTRY_ACTION = IconLoader.getIcon("/icons/end_point.png", StateMachineIcons.class);
    Icon EXIT_ACTION = IconLoader.getIcon("/icons/start_point.png", StateMachineIcons.class);
}
