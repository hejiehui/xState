package com.xrosstools.xstate.idea.editor;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.xstate.idea.editor.actions.StateMachineCreateEventAction;
import com.xrosstools.xstate.idea.editor.actions.StateMachineMessages;
import com.xrosstools.xstate.idea.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.idea.editor.commands.SelectReferenceCommand;
import com.xrosstools.xstate.idea.editor.model.*;
import com.xrosstools.xstate.idea.editor.parts.StateMachinePart;
import com.xrosstools.xstate.idea.editor.parts.StateNodePart;
import com.xrosstools.xstate.idea.editor.parts.StateTransitionPart;

import javax.swing.*;
import java.util.Objects;

import static com.xrosstools.xstate.idea.editor.model.StateMachineConstants.*;

public class StateMachineContextMenuProvider extends ContextMenuProvider implements StateMachineMessages {
	private Project project;

	public StateMachineContextMenuProvider(Project project) {
        this.project = project;
    }

    public JPopupMenu buildContextMenu(Object selected) {
        JPopupMenu menu = new JPopupMenu();
        EditPart part = (EditPart)selected;

        if(part instanceof StateNodePart) {
            StateNode node = (StateNode)part.getModel();
            StateMachineDiagram diagram = (StateMachineDiagram)part.getParent().getParent().getModel();

            buildStateNodeContextMenu(project, menu, node, diagram);
        } else if(part instanceof StateMachinePart) {
            StateMachine machine = (StateMachine)part.getModel();
            buildStateMachineContextMenu(project, menu, machine);
        } else if(part instanceof StateTransitionPart) {
            StateTransitionPart transPart = (StateTransitionPart)part;
            StateMachine machine = (StateMachine)transPart.getTarget().getParent().getModel();
            buildStateTransitionContextMenu(project, menu, (StateTransition)part.getModel(), machine);
        }

        return menu;
    }

    public static void buildStateNodeContextMenu(Project project, JPopupMenu menu, StateNode node, StateMachineDiagram diagram) {
        if(node instanceof StartNode || node instanceof EndNode)
            return;

        buildModifyImplementationMenu(project, menu, node, PROP_ENTRY_ACTION);

        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, node, PROP_EXIT_ACTION);

        addSeparator(menu);

        //Add reference to child State Machine
        String refName = node.getReference();
        JMenu referenceMenu = new JMenu(REFERENCE_MSG + (refName == null ? "" : refName));
        for(StateMachine machine: diagram.getMachines()) {
            String name = machine.getName();
            if(!Objects.equals(name, refName))
                referenceMenu.add(createItem(name, (refName != null && refName.equals(name)), new SelectReferenceCommand(node, name)));
        }
        menu.add(referenceMenu);
    }

    public static void buildStateMachineContextMenu(Project project, JPopupMenu menu, StateMachine machine) {
        menu.add(createItem(new StateMachineCreateEventAction(project, machine)));
    }

    public static void buildStateTransitionContextMenu(Project project, JPopupMenu menu, StateTransition transition, StateMachine machine) {
        //Create and assign event
        menu.add(createItem(new StateMachineCreateEventAction(project, machine, transition)));
        addSeparator(menu);

        String evtId = transition.getEvent() == null ? "" : transition.getEvent().getDisplayText();
        JMenu eventMenu = new JMenu(ON_EVENT_MSG + evtId);
        for(Event e: transition.getHelper().getEvents()) {
            if (transition.getEvent() != e)
                eventMenu .add(createItem(e.getDisplayText(), transition.getEvent() == e, new SelectEventCommand(transition, e)));
        }
        menu.add(eventMenu);

        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, transition, PROP_TRANSITION_ACTION);
        addSeparator(menu);

        buildModifyImplementationMenu(project, menu, transition, PROP_TRANSITION_GUARD);
    }

    private static void buildModifyImplementationMenu(Project project, JPopupMenu menu, IPropertySource source, String propertyName) {
	    ImplementationUtil.buildImplementationMenu(project, menu, source, propertyName, true);
	}
}
