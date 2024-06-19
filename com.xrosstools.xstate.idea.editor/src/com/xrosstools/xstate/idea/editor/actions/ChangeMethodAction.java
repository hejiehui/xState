package com.xrosstools.xstate.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xstate.idea.editor.commands.Accessor;
import com.xrosstools.xstate.idea.editor.commands.ChangeMethodCommand;

public class ChangeMethodAction extends Action implements StateMachineMessages {
    private Accessor accessor;
    private String methodName;
    public ChangeMethodAction(Accessor accessor, String methodName){
        setText(methodName);
        this.accessor = accessor;
        this.methodName = methodName;
        setChecked(methodName.equals(accessor.getMethodName()));
    }

    public Command createCommand() {
        return new ChangeMethodCommand(accessor, methodName);
    }
}
