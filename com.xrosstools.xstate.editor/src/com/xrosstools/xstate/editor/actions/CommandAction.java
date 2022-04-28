package com.xrosstools.xstate.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

public class CommandAction extends WorkbenchPartAction {
    private Command command;
    public CommandAction(IWorkbenchPart part, String text, boolean checked, Command command){
        super(part);
        setText(text);
        setChecked(checked);
        this.command = command;
    }
    
    protected boolean calculateEnabled() {
        return true;
    }
    
    public void run() {
        execute(command);
    }
}
