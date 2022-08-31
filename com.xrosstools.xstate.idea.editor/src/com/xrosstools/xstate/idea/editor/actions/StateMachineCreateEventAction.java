package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.BaseDialogAction;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.commands.CommandChain;
import com.xrosstools.xstate.idea.editor.commands.AddEventCommand;
import com.xrosstools.xstate.idea.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;
import com.xrosstools.xstate.idea.editor.model.StateTransition;

public class StateMachineCreateEventAction extends BaseDialogAction implements StateMachineActionConstants, StateMachineMessages{
	private StateMachine machine;
    private StateTransition transition;
	public StateMachineCreateEventAction(Project project, StateMachine machine){
		super(project, CREATE_EVENT_MSG, "Event", "event");
		this.machine = machine;
	}

	public StateMachineCreateEventAction(Project project, StateMachine machine, StateTransition transition){
		this(project, machine);
		this.transition = transition;
	}

	@Override
	public Command createCommand(String value) {
		Event event = new Event();
		event.setId(value);

        Command addEvt = new AddEventCommand(machine, event);
		if(transition == null)
		    return addEvt;

        CommandChain cc = new CommandChain();
        cc.add(addEvt);
        cc.add(new SelectEventCommand(transition,event));
        return cc;

    }
}
