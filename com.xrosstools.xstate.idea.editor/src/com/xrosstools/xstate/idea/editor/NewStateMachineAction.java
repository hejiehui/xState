package com.xrosstools.xstate.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;

public class NewStateMachineAction extends DefaultNewModelFileAction {
    private static final String modelTypeName = "Xross State Machine";
    private static final String templatePath = "/template/emptyTemplate.xstate";
    private static final String newFileName = "new_xstate_file";

    public NewStateMachineAction() {
        super(modelTypeName, StateMachineFileType.EXTENSION, StateMachineIcons.STATE_MACHINE_DIAGRAM, newFileName, templatePath);
    }
}
