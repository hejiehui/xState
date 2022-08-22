package com.xrosstools.xstate.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.BaseDialogAction;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.AddEventCommand;
import com.xrosstools.xstate.idea.editor.model.Event;
import com.xrosstools.xstate.idea.editor.model.StateMachine;

public class StateMachineCreateEventAction extends BaseDialogAction implements StateMachineActionConstants, StateMachineMessages{
	private StateMachine machine;
	public StateMachineCreateEventAction(Project project, StateMachine machine){
		super(project, CREATE_EVENT_MSG, "Event", "event");
		this.machine = machine;
	}

	@Override
	public Command createCommand(String value) {
		Event event = new Event();
		event.setId(value);
		return new AddEventCommand(machine, event);
	}
}
