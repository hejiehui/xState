package com.xrosstools.xstate.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xrosstools.xstate.editor.commands.DeleteTransitionCommand;
import com.xrosstools.xstate.editor.model.StateTransition;

public class StateNodeConnectionEditPolicy extends ComponentEditPolicy{

    protected Command createDeleteCommand(GroupRequest deleteRequest) {
        return new DeleteTransitionCommand((StateTransition)getHost().getModel());
    }
}
