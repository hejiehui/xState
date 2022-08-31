package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.WorkbenchPart;

import com.xrosstools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xrosstools.xstate.editor.commands.AddEventCommand;
import com.xrosstools.xstate.editor.commands.SelectEventCommand;
import com.xrosstools.xstate.editor.model.Event;
import com.xrosstools.xstate.editor.model.StateMachine;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateMachineCreateEventAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateMachine machine;
	private StateTransition transition;
	public StateMachineCreateEventAction(IWorkbenchPart part, StateMachine machine){
		super(part);
		setId(ID_PREFIX + CREATE_EVENT);
		setText(CREATE_EVENT_MSG);
		this.machine = machine;
	}
	
    public StateMachineCreateEventAction(IWorkbenchPart part, StateMachine machine, StateTransition transition){
        this(part, machine);
        this.transition = transition;
    }
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Create new Event: ", "Event", "event", null);
		if (dlg.open() != Window.OK)
			return;
		String name = dlg.getValue();
		
		StateMachineDiagramGraphicalEditor editor = (StateMachineDiagramGraphicalEditor)getWorkbenchPart();
		Event event = new Event();
		event.setId(name);
		execute(new AddEventCommand(machine, event));
		
		if(transition == null)
		    return;

		execute(new SelectEventCommand(transition, event));
	}
}
