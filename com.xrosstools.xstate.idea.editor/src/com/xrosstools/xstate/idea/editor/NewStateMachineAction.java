package com.xrosstools.xstate.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;

public class NewStateMachineAction extends DefaultNewModelFileAction {
    public NewStateMachineAction() {
        super("Xross State Machine", StateMachineFileType.EXTENSION, StateMachineFileType.ICON, "new_xstate_file", "/template/emptyTemplate.xstate");
    }
}
