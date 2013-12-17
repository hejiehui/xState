package com.xross.tools.xstate.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xstate.editor.StateMachineDiagramGraphicalEditor;
import com.xross.tools.xstate.editor.model.Event;
import com.xross.tools.xstate.editor.model.StateTransition;

public class StateMachineChangeTransitionAction extends WorkbenchPartAction implements StateMachineActionConstants, StateMachineMessages{
	private StateTransition transition;
	public StateMachineChangeTransitionAction(IWorkbenchPart part, StateTransition transition){
		super(part);
		setId(ID_PREFIX + CREATE_EVENT);
		setText(CREATE_EVENT_MSG);
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
//		execute(new AddEventCommand(machine, event));
	}
}
